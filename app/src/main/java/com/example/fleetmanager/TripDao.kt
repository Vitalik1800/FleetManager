package com.example.fleetmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getTripsByCar(): LiveData<List<Trip>>

    @Query("SELECT * FROM trips WHERE carId = :carId")
    fun getTripsByCar(carId: Long): LiveData<List<Trip>>

    @Query("SELECT * FROM trips")
    suspend fun getAllTripsSync(): List<Trip>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: Trip): Long

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trips: List<Trip>)
}
