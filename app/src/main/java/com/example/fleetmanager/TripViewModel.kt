package com.example.fleetmanager

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val db: FleetDatabase = FleetDatabase.getDatabase(application)
    private val tripDao: TripDao = db.tripDao()
    private val carDao: CarDao = db.carDao()
    private val firebaseService = FirebaseService()

    private val _allTrips = MutableLiveData<List<Trip>>()
    val allTrips: LiveData<List<Trip>> get() = _allTrips

    init {
        loadTrips()
    }

    private fun loadTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            val trips = tripDao.getAllTripsSync()
            _allTrips.postValue(trips)
        }
    }

    fun insertTrip(carId: Long, trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            val car = carDao.getCarById(carId)
            if (car != null) {
                val tripId = tripDao.insertTrip(trip)
                firebaseService.addTripToFirebase(trip.copy(id = tripId))
                loadTrips()
            } else {
                Log.e("TripViewModel", "Car with id $carId does not exist")
            }
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            tripDao.updateTrip(trip)
            firebaseService.updateTripInFirebase(trip.id.toString(), trip)
            loadTrips()
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            tripDao.deleteTrip(trip)
            firebaseService.deleteTripFromFirebase(trip.id.toString())
            loadTrips()
        }
    }

}
