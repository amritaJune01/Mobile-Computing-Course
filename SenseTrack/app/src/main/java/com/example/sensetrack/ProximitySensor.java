package com.example.sensetrack;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProximitySensor {
    public int getSid() {
        return sid;
    }

    public double getProximityVal() {
        return proximityVal;
    }

    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo(name = "Proximity_Value")
    public double proximityVal;

    public ProximitySensor(int sid, double proximityVal) {
        this.sid = sid;
        this.proximityVal = proximityVal;
    }
}