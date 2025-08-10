package com.example.fleetmanager

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "trips",
    foreignKeys = [ForeignKey(entity = Car::class,
        parentColumns = ["id"],
        childColumns = ["carId"],
        onDelete = ForeignKey.CASCADE)])
data class Trip(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val carId: Long,
    val date: String,
    val distance: Double,
    val fuelConsumption: Double
){
    constructor() : this(0, 0, "", 0.0, 0.0)
}
