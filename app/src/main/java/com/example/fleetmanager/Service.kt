package com.example.fleetmanager

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "services",
    foreignKeys = [ForeignKey(entity = Car::class,
        parentColumns = ["id"],
        childColumns = ["carId"],
        onDelete = ForeignKey.CASCADE)])
data class Service(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val carId: Long,
    val serviceDate: String,
    val serviceType: String,
    val cost: Double
)
