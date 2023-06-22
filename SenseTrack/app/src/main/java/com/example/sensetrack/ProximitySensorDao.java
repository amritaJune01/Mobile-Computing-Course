package com.example.sensetrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProximitySensorDao {

    @Query("SELECT * FROM proximitySensor")
    List<ProximitySensor> getAll();

    @Query("SELECT * FROM proximitySensor WHERE sid IN (:sensorIds)")
    List<ProximitySensor> loadAllByIds(double[] sensorIds);

    @Insert
    void insertProximitySensorVal(ProximitySensor proximitySensor);


}