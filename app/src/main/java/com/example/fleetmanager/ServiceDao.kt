package com.example.fleetmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ServiceDao {

    @Insert
    suspend fun insertService(service: Service) : Long

    @Update
    suspend fun updateService(service: Service)

    @Delete
    suspend fun deleteService(service: Service)

    @Query("SELECT * FROM services")
    fun getServicesByCar() : LiveData<List<Service>>

    @Query("SELECT * FROM services WHERE carId = :carId")
    fun getServicesByCar(carId: Long) : LiveData<List<Service>>

    @Query("SELECT * FROM services")
    suspend fun getAllServicesSync(): List<Service>
}