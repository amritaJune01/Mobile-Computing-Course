package com.example.motionwalk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor geoRotationVectorSensor, magnetometer;
    TextView geo, step_count, direction, displacement, stairs, lift,strideL;
    SwitchMaterial toggleGeo;
    MapView mapView;

    LocationManager locationManager;
    Location curr_location;

    float[] rotationMatrix = new float[9];
    float[] orientationAngles = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientation = new float[3];
    private float[] mAccelerometerValues;
    private final float[] mRotationMatrixFromVector = new float[9];
    public float[] orientationVals = new float[3];

    float strideLength = (float) (0.415 * 1.8 - 0.00162 * 53 - 0.186);

    boolean showMap = false;
    boolean isPhoneAligned = false;

    private int steps = 0;
    private float displacement_m = 0;

    double latitude, longitude;

    DecimalFormat df_obj = new DecimalFormat("#.##");

    double brng = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geo = findViewById(R.id.geo_value);
        step_count = findViewById(R.id.step_count);
        direction = findViewById(R.id.direction);
        displacement = findViewById(R.id.displacement);
        stairs = findViewById(R.id.stairs);
        lift = findViewById(R.id.lift);
        mapView = findViewById(R.id.mapView);
        toggleGeo = findViewById(R.id.geo_toggle_button);
        strideL = findViewById(R.id.stride);

        strideL.setText("Stride Length: "+String.valueOf(strideLength));


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        geoRotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);


        toggleGeo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton group, boolean isChecked) {

                if (isChecked) {
                    sensorManager.registerListener(geoSensorEventListener, geoRotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    sensorManager.unregisterListener(geoSensorEventListener);
                    sensorManager.unregisterListener(accSensorEventListener);
                    geo.setText("Switch on Geometric Rotation Vector to align to earth's surface.");
                    step_count.setText("Step Count: 0");
                    displacement.setText("Displacement: 0 m");
                    sensorManager.unregisterListener(magSensorEventListener);
                    direction.setText("Direction: ");
                    showMap = false;
                    mapView.setMinimumHeight(10);
                    mapView.setVisibility(View.INVISIBLE);
                    steps = 0;
                    displacement_m = 0;
                    isPhoneAligned = false;
                    stairs.setText("Stairs Detected: ");
                    lift.setText("Lift Detected: ");
                }
            }
        });
    }


    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            curr_location = location;
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            showMap(latitude, longitude);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    void showMap(double latitude, double longitude) {
        Configuration.getInstance().setUserAgentValue(getPackageName());

        mapView.setVisibility(View.VISIBLE);
        mapView.setMinimumHeight(300);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setUseDataConnection(true);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(20.0);
        mapView.getController().setCenter(new GeoPoint(latitude, longitude));

        Marker marker = new Marker(mapView);
        Drawable markerIcon = getResources().getDrawable(R.drawable.ic_baseline_location_on_24);
        marker.setPosition(new GeoPoint(latitude, longitude));
        marker.setIcon(markerIcon);
        mapView.getOverlayManager().add(marker);
        locationManager.removeUpdates(locationListener);
    }


    public Location computeNextStep(float stepSize, float bearing) {
//        if (curr_location == null) {
//            // setting a default location if we fail to get curr loc
//            Location newLoc = new Location("");
//            newLoc.setLatitude(28.7041);
//            newLoc.setLongitude(77.1025);
//            return newLoc;
//        }
        Location new_location = new Location(curr_location);
        float angDistance = stepSize / 6371000;
        double old_Latitude = curr_location.getLatitude();
        double old_Longitude = curr_location.getLongitude();
        double new_Latitude = Math.asin(Math.sin(Math.toRadians(old_Latitude)) * Math.cos(angDistance) + Math.cos(Math.toRadians(old_Latitude)) * Math.sin(angDistance) * Math.cos(bearing));
        double new_Longitude = Math.toRadians(old_Longitude) + Math.atan2(Math.sin(bearing) * Math.sin(angDistance) * Math.cos(Math.toRadians(old_Latitude)), Math.cos(angDistance) - Math.sin(Math.toRadians(old_Latitude)) * Math.sin(new_Latitude));
        new_location.setLatitude(Math.toDegrees(new_Latitude));
        new_location.setLongitude(Math.toDegrees(new_Longitude));
        new_location.setBearing((curr_location.getBearing() + 180) % 360);
        curr_location = new_location;
        return new_location;
    }


    SensorEventListener geoSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {

                sensorManager.registerListener(accSensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(magSensorEventListener, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                SensorManager.getOrientation(rotationMatrix, orientationAngles);

                float azimuth = orientationAngles[0];
                float pitch = orientationAngles[1];
                float roll = orientationAngles[2];

                float azimuthD = (float) Math.toDegrees(azimuth);
                float pitchD = (float) Math.toDegrees(pitch);
                float rollD = (float) Math.toDegrees(roll);

                String direction = " ";

                if (Math.abs(pitchD) < 12.0 && Math.abs(rollD) < 12.0) {
                    if (azimuthD >= -20.0 && azimuthD <= 20.0) {
                        geo.setText("Your phone is oriented with the earth's frame of reference !!\n");
                        isPhoneAligned = true;
                        if (!showMap) {
                            showMap = true;
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        }
                    } else if (azimuthD < -20) {
                        if (!isPhoneAligned)
                            geo.setText("Rotate your phone clockwise by " + String.format("%.1f", Math.abs(azimuthD)) + " degrees.\n");
                    } else {
                        if (!isPhoneAligned)
                            geo.setText("Rotate your phone anticlockwise by " + String.format("%.1f", Math.abs(azimuthD)) + " degrees.\n");

                    }

                } else {
                    if (Math.abs(rollD) >= 12.0) {
                        if (rollD < 0 && rollD >= -30)
                            direction += "Turn your phone right by " + String.format("%.1f", rollD * (-1)) + " degrees.\n";
                        else if (rollD >= 0 && rollD <= 30)
                            direction += "Turn your phone left by " + String.format("%.1f", rollD) + " degrees.\n";
                        else direction += "Make your phone's screen face the ceiling.\n";
                        if (!isPhoneAligned) geo.setText(direction);
                    }

                    if (Math.abs(pitchD) >= 12.0) {
                        if (pitchD < 0)
                            direction += "Put your phone down by " + String.format("%.1f", pitchD * (-1)) + " degrees, such that it faces the ceiling.\n";
                        else
                            direction += "Put your phone up by " + String.format("%.1f", pitchD) + " degrees, such that it faces the ceiling.\n";
                        if (!isPhoneAligned) geo.setText(direction);
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    private final SensorEventListener accSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                mAccelerometerValues = event.values;
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                x = (int) Math.round(x);
                y = (int) Math.round(y);
                z = (int) Math.round(z);


                if (z > 11) {
                    steps++;
                    displacement_m = (strideLength * steps);

                    Location newLoc = computeNextStep(strideLength, (float) brng);
                    curr_location = newLoc;


                    mapView.getController().setCenter(new GeoPoint(curr_location.getLatitude(), curr_location.getLongitude()));
                    Marker marker = new Marker(mapView);
                    marker.setPosition(new GeoPoint(new GeoPoint(curr_location.getLatitude(), curr_location.getLongitude())));
                    Drawable markerIcon = getResources().getDrawable(R.drawable.ic_baseline_circle_24);
                    marker.setIcon(markerIcon);
                    mapView.getOverlayManager().add(marker);

                }

                step_count.setText(String.valueOf("Step Count: " + steps));
                displacement.setText(String.valueOf("Displacement: " + displacement_m + " m"));


                float x1 = event.values[0];
                float y1 = event.values[1];
                float z1 = event.values[2];


                x1 = Float.parseFloat(df_obj.format(x1));
                y1 = Float.parseFloat(df_obj.format(y1));
                z1 = Float.parseFloat(df_obj.format(z1));


                if (z1 >= (float) 13.0 || z1 <= (float) 7.0) stairs.setText("Stairs Detected: Yes");
                else stairs.setText("Stairs Detected: ");

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    };


    private final SensorEventListener magSensorEventListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                float[] magneticValues = event.values.clone();

                SensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerValues, magneticValues);

                SensorManager.getOrientation(mRotationMatrix, mOrientation);


                SensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector, event.values);
                SensorManager.remapCoordinateSystem(mRotationMatrixFromVector, SensorManager.AXIS_X, SensorManager.AXIS_Z, mRotationMatrix);
                SensorManager.getOrientation(mRotationMatrix, orientationVals);


                float[] magneticFieldValues = event.values;
                float magnitude = (float) Math.sqrt(magneticFieldValues[0] * magneticFieldValues[0] + magneticFieldValues[1] * magneticFieldValues[1] + magneticFieldValues[2] * magneticFieldValues[2]);


                if (magnitude <= (float) 21.0) {
                    lift.setText("Lift Detected: Yes");
                } else lift.setText("Lift Detected: ");


                brng = mOrientation[0];
                float azimuthDegrees = (float) Math.toDegrees(mOrientation[0]);
                float pitchDegrees = (float) Math.toDegrees(mOrientation[1]);
                float rollDegrees = (float) Math.toDegrees(mOrientation[2]);

                String dir = "";

                float degrees = azimuthDegrees;

                if (degrees >= -22.5 && degrees < 22.5) {
                    dir = "North";
                } else if (degrees >= 22.5 && degrees < 67.5) {
                    dir = "North East";
                } else if (degrees >= 67.5 && degrees < 112.5) {
                    dir = "East";
                } else if (degrees >= 112.5 && degrees < 157.5) {
                    dir = "South East";
                } else if (degrees >= 157.5 || degrees < -157.5) {
                    dir = "South";
                } else if (degrees >= -157.5 && degrees < -112.5) {
                    dir = "South West";
                } else if (degrees >= -112.5 && degrees < -67.5) {
                    dir = "West";
                } else if (degrees >= -67.5 && degrees < -22.5) {
                    dir = "North West";
                }
                direction.setText("Moving towards: " + dir + " direction");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}


