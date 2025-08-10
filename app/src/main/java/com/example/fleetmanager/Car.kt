package com.example.fleetmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val model: String,
    val year: Int,
    val plateNumber: String
){
    constructor() : this(0, "", 0, "")
}

