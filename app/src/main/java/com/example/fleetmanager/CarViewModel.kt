package com.example.fleetmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {

    private val db: FleetDatabase = FleetDatabase.getDatabase(application)
    private val carDao: CarDao = db.carDao()
    private val firebaseService = FirebaseService()
    private val _allCars = MutableLiveData<List<Car>>()
    val allCars: LiveData<List<Car>> get() = _allCars

    init{
        loadCars()
    }

    private fun loadCars() {
        viewModelScope.launch(Dispatchers.IO) {
            val cars = carDao.getAllCarsSync()
            _allCars.postValue(cars)
        }
    }


    fun insertCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            val carId = carDao.insertCar(car)
            firebaseService.addCarToFirebase(car.copy(id = carId))
            loadCars()
        }
    }

    fun updateCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carDao.updateCar(car)
            firebaseService.updateCarInFirebase(car.id.toString(), car)
            loadCars()
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carDao.deleteCar(car)
            firebaseService.deleteCarFromFirebase(car.id.toString())
            loadCars()
        }
    }
}
