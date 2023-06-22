package com.example.sensetrack;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GeoMagneticRotationVectorSensor {
    public int getSid() {
        return sid;
    }

    public double getGeoMagneticRotationVectorVal1() {
        return geoMagneticRotationVectorVal1;
    }

    public double getGeoMagneticRotationVectorVal2() {
        return geoMagneticRotationVectorVal2;
    }

    public double getGeoMagneticRotationVectorVal3() {
        return geoMagneticRotationVectorVal3;
    }

    public double getGeoMagneticRotationVectorVal4() {
        return geoMagneticRotationVectorVal4;
    }

    public double getGeoMagneticRotationVectorVal5() {
        return geoMagneticRotationVectorVal5;
    }

    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo(name = "GeoMagneticRotationVector_Value1")
    public double geoMagneticRotationVectorVal1;

    @ColumnInfo(name = "GeoMagneticRotationVector_Value2")
    public double geoMagneticRotationVectorVal2;

    @ColumnInfo(name = "GeoMagneticRotationVector_Value3")
    public double geoMagneticRotationVectorVal3;

    @ColumnInfo(name = "GeoMagneticRotationVector_Value4")
    public double geoMagneticRotationVectorVal4;

    @ColumnInfo(name = "GeoMagneticRotationVector_Value5")
    public double geoMagneticRotationVectorVal5;

    public GeoMagneticRotationVectorSensor(int sid, double geoMagneticRotationVectorVal1, double geoMagneticRotationVectorVal2, double geoMagneticRotationVectorVal3, double geoMagneticRotationVectorVal4, double geoMagneticRotationVectorVal5) {
        this.sid = sid;
        this.geoMagneticRotationVectorVal1 = geoMagneticRotationVectorVal1;
        this.geoMagneticRotationVectorVal2 = geoMagneticRotationVectorVal2;
        this.geoMagneticRotationVectorVal3 = geoMagneticRotationVectorVal3;
        this.geoMagneticRotationVectorVal4 = geoMagneticRotationVectorVal4;
        this.geoMagneticRotationVectorVal5 = geoMagneticRotationVectorVal5;
    }
}