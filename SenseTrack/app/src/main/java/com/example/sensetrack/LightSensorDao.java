package com.example.sensetrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LightSensorDao {

    @Query("SELECT * FROM lightSensor")
    List<LightSensor> getAll();

    @Query("SELECT * FROM lightSensor WHERE sid IN (:sensorIds)")
    List<LightSensor> loadAllByIds(double[] sensorIds);

    @Insert
    void insertLightSensorVal(LightSensor lightSensor);


}