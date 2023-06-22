package com.example.sensetrack;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LightSensor.class}, version = 1)
public abstract class LightSensorDatabase extends RoomDatabase {
    public abstract LightSensorDao lightSensorDao();
}
