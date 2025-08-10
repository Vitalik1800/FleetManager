package com.example.fleetmanager

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "expenses",
    foreignKeys = [ForeignKey(entity = Car::class,
        parentColumns = ["id"],
        childColumns = ["carId"],
        onDelete = ForeignKey.CASCADE)])
data class Expense (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val carId: Long,
    val expenseDate: String,
    val description: String,
    val amount: Double
)
