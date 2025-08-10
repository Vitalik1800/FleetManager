package com.example.fleetmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class, Trip::class, Service::class, Expense::class], version = 1)
abstract class FleetDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao
    abstract fun tripDao(): TripDao
    abstract fun serviceDao(): ServiceDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: FleetDatabase? = null

        fun getDatabase(context: Context): FleetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FleetDatabase::class.java,
                    "fleet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}