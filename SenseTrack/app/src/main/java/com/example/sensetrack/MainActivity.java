package com.example.sensetrack;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor proximitySensor, lightSensor, geoRotationVectorSensor;
    TextView proximity, light, geo;
    SwitchMaterial toggleProximity, toggleLight, toggleGeo;

    float[] rotationMatrix = new float[9];
    float[] orientationAngles = new float[3];


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proximity = findViewById(R.id.proximity_value);
        light = findViewById(R.id.light_value);
        geo = findViewById(R.id.geo_value);


        toggleProximity = findViewById(R.id.proximity_toggle_button);
        toggleLight = findViewById(R.id.light_toggle_button);
        toggleGeo = findViewById(R.id.geo_toggle_button);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        geoRotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);


        toggleProximity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton group, boolean isChecked) {

                if (isChecked) {
                    sensorManager.registerListener(proximitySensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    sensorManager.unregisterListener(proximitySensorEventListener);
                    proximity.setText("Switch on Proximity Sensor to see values.");
                }
            }
        });

        toggleLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton group, boolean isChecked) {

                if (isChecked) {
                    sensorManager.registerListener(lightSensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    sensorManager.unregisterListener(lightSensorEventListener);
                    light.setText("Switch on Light Sensor to see values.");
                }
            }
        });

        toggleGeo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton group, boolean isChecked) {

                if (isChecked) {
                    sensorManager.registerListener(geoSensorEventListener, geoRotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    sensorManager.unregisterListener(geoSensorEventListener);
                    geo.setText("Switch on Geometric Rotation Vector to align to earth's surface.");
                }
            }
        });


    }

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // method to check accuracy changed in sensor.
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                proximity.setText(String.valueOf(event.values[0]));
                if (event.values[0] < 5) {
                    new proximitySensorThread((double) event.values[0]).start();
                    Log.i("proximity_sensor_val", String.valueOf(event.values[0]));
                }
            }
        }
    };


    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // method to check accuracy changed in sensor.
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                light.setText(String.valueOf(event.values[0]));
                if (event.values[0] <= 8) {
                    new lightSensorThread((double) event.values[0]).start();
                    Log.i("light_sensor_val", String.valueOf(event.values[0]));
                }
            }
        }
    };


    SensorEventListener geoSensorEventListener = new SensorEventListener() {


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // method to check accuracy changed in sensor.
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {

                new geoMagneticRotationVectorSensorThread((double) event.values[0], (double) event.values[1], (double) event.values[2], (double) event.values[3], (double) event.values[4]).start();

                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                SensorManager.getOrientation(rotationMatrix, orientationAngles);


                float azimuth = orientationAngles[0];
                float pitch = orientationAngles[1];
                float roll = orientationAngles[2];

                float azimuthD = (float) Math.toDegrees(azimuth);
                float pitchD = (float) Math.toDegrees(pitch);
                float rollD = (float) Math.toDegrees(roll);

                String direction = " ";


                if (Math.abs(pitchD) < 4.0 && Math.abs(rollD) < 4.0) {
                    if (azimuthD >= 0 && azimuthD <= 4.0)
                        geo.setText("Your phone is oriented with the earth's frame of reference !!\n");
                    else if (azimuthD < 0)
                        geo.setText("Rotate your phone, facing the ceiling clockwise by " + String.format("%.1f", Math.abs(azimuthD)) + " degrees.\n\n");
                    else
                        geo.setText("Rotate your phone, facing the ceiling anticlockwise by " + String.format("%.1f", Math.abs(azimuthD)) + " degrees.\n\n");
                } else {

                    if (Math.abs(rollD) >= 4.0) {

                        if (rollD < 0 && rollD >= -30)
                            direction += "Tilt your phone right by " + String.format("%.1f", rollD * (-1)) + " degrees, facing the ceiling\n\n";
                        else if (rollD >= 0 && rollD <= 30)
                            direction += "Tilt your phone left by " + String.format("%.1f", rollD) + " degrees, facing the ceiling\n\n";
                        else
                            direction += "Make your phone's screen face the ceiling.\n\n";
                        ;
                        geo.setText(direction);

                    }

                    if (Math.abs(pitchD) >= 4.0) {
                        if (pitchD < 0)
                            direction += "Put your phone down by " + String.format("%.1f", pitchD * (-1)) + " degrees, such that it faces the ceiling.\n";
                        else
                            direction += "Put your phone up by " + String.format("%.1f", pitchD) + " degrees, such that it faces the ceiling.\n";
                        geo.setText(direction);
                    }

                }
            }
        }
    };


    class proximitySensorThread extends Thread {

        double proximitySensorVal;

        public proximitySensorThread(double proximitySensorVal) {
            this.proximitySensorVal = proximitySensorVal;
        }

        @Override
        public void run() {
            super.run();

            ProximitySensorDatabase proximitySensorDatabase = Room.databaseBuilder(getApplicationContext(), ProximitySensorDatabase.class, "Proximity_Sensor_Database").build();

            ProximitySensorDao proximitySensorDao = proximitySensorDatabase.proximitySensorDao();

            proximitySensorDao.insertProximitySensorVal(new ProximitySensor(0, proximitySensorVal));

        }
    }


    class lightSensorThread extends Thread {

        double lightSensorVal;

        public lightSensorThread(double lightSensorVal) {
            this.lightSensorVal = lightSensorVal;
        }

        @Override
        public void run() {
            super.run();

            LightSensorDatabase lightSensorDatabase = Room.databaseBuilder(getApplicationContext(), LightSensorDatabase.class, "Light_Sensor_Database").build();

            LightSensorDao lightSensorDao = lightSensorDatabase.lightSensorDao();

            lightSensorDao.insertLightSensorVal(new LightSensor(0, lightSensorVal));

        }
    }

    class geoMagneticRotationVectorSensorThread extends Thread {

        double geoMagneticRotationVectorSensorVal1;
        double geoMagneticRotationVectorSensorVal2;
        double geoMagneticRotationVectorSensorVal3;
        double geoMagneticRotationVectorSensorVal4;
        double geoMagneticRotationVectorSensorVal5;

        public geoMagneticRotationVectorSensorThread(double geoMagneticRotationVectorSensorVal1, double geoMagneticRotationVectorSensorVal2, double geoMagneticRotationVectorSensorVal3, double geoMagneticRotationVectorSensorVal4, double geoMagneticRotationVectorSensorVal5) {
            this.geoMagneticRotationVectorSensorVal1 = geoMagneticRotationVectorSensorVal1;
            this.geoMagneticRotationVectorSensorVal2 = geoMagneticRotationVectorSensorVal2;
            this.geoMagneticRotationVectorSensorVal3 = geoMagneticRotationVectorSensorVal3;
            this.geoMagneticRotationVectorSensorVal4 = geoMagneticRotationVectorSensorVal4;
            this.geoMagneticRotationVectorSensorVal5 = geoMagneticRotationVectorSensorVal5;
        }

        @Override
        public void run() {
            super.run();

            GeoMagneticRotationVectorSensorDatabase geoMagneticRotationVectorSensorDatabase = Room.databaseBuilder(getApplicationContext(), GeoMagneticRotationVectorSensorDatabase.class, "GeoMagneticRotationVector_Sensor_Database").build();

            GeoMagneticRotationVectorSensorDao geoMagneticRotationVectorSensorDao = geoMagneticRotationVectorSensorDatabase.geoMagneticRotationVectorSensorDao();

            geoMagneticRotationVectorSensorDao.insertGeoMagneticRotationVectorSensorVal(new GeoMagneticRotationVectorSensor(0, geoMagneticRotationVectorSensorVal1, geoMagneticRotationVectorSensorVal2, geoMagneticRotationVectorSensorVal3, geoMagneticRotationVectorSensorVal4, geoMagneticRotationVectorSensorVal5));

        }
    }

}