package com.example.sensetrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GeoMagneticRotationVectorSensorDao {

    @Query("SELECT * FROM geoMagneticRotationVectorSensor")
    List<GeoMagneticRotationVectorSensor> getAll();

    @Query("SELECT * FROM geoMagneticRotationVectorSensor WHERE sid IN (:sensorIds)")
    List<GeoMagneticRotationVectorSensor> loadAllByIds(double[] sensorIds);

    @Insert
    void insertGeoMagneticRotationVectorSensorVal(GeoMagneticRotationVectorSensor geoMagneticRotationVectorSensor);

}