package com.example.sensetrack;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LightSensor {
    public int getSid() {
        return sid;
    }

    public double getLightVal() {
        return lightVal;
    }

    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo(name = "Light_Value")
    public double lightVal;

    public LightSensor(int sid, double lightVal) {
        this.sid = sid;
        this.lightVal = lightVal;
    }
}