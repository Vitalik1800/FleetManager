package com.example.fleetmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: Expense) : Long

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses")
    fun getExpensesByCar() : LiveData<List<Expense>>

    @Query("SELECT * FROM expenses WHERE carId = :carId")
    fun getExpensesByCar(carId: Long) : LiveData<List<Expense>>

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpensesSync(): List<Expense>
}