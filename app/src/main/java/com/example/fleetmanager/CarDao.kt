package com.example.fleetmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarDao {

    @Insert
    suspend fun insertCar(car: Car) : Long

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("SELECT * FROM cars")
    fun getAllCars(): LiveData<List<Car>>

    @Query("SELECT * FROM cars WHERE id = :carId LIMIT 1")
    suspend fun getCarById(carId: Long): Car?

    @Query("SELECT * FROM cars")
    suspend fun getAllCarsSync(): List<Car>
}