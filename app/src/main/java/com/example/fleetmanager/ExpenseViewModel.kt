package com.example.fleetmanager

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val db: FleetDatabase = FleetDatabase.getDatabase(application)
    private val expenseDao: ExpenseDao = db.expenseDao()
    private val carDao: CarDao = db.carDao()
    private val firebaseService = FirebaseService()

    private val _allExpenses = MutableLiveData<List<Expense>>()
    val allExpenses: LiveData<List<Expense>> get() = _allExpenses

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            val expenses = expenseDao.getAllExpensesSync()
            _allExpenses.postValue(expenses)
        }
    }

    fun insertExpense(carId: Long, expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            val car = carDao.getCarById(carId)
            if (car != null) {
                val expenseId = expenseDao.insertExpense(expense)
                firebaseService.addExpenseToFirebase(expense.copy(id = expenseId))
                loadExpenses()
            } else {
                Log.e("ExpenseViewModel", "Car with id $carId does not exist")
            }
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.updateExpense(expense)
            firebaseService.updateExpenseInFirebase(expense.id.toString(), expense)
            loadExpenses()
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.deleteExpense(expense)
            firebaseService.deleteExpenseFromFirebase(expense.id.toString())
            loadExpenses()
        }
    }

}
