package com.example.sensetrack;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GeoMagneticRotationVectorSensor.class}, version = 1)
public abstract class GeoMagneticRotationVectorSensorDatabase extends RoomDatabase {
    public abstract GeoMagneticRotationVectorSensorDao geoMagneticRotationVectorSensorDao();
}
