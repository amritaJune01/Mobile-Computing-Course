package com.example.sensetrack;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProximitySensor.class}, version = 1)
public abstract class ProximitySensorDatabase extends RoomDatabase {
    public abstract ProximitySensorDao proximitySensorDao();
}
