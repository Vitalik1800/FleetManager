package com.example.fleetmanager

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiceViewModel(application: Application) : AndroidViewModel(application) {

    private val db: FleetDatabase = FleetDatabase.getDatabase(application)
    private val serviceDao: ServiceDao = db.serviceDao()
    private val carDao: CarDao = db.carDao()
    private val firebaseService = FirebaseService()

    private val _allServices = MutableLiveData<List<Service>>()
    val allServices: LiveData<List<Service>> get() = _allServices

    init {
        loadServices()
    }

    private fun loadServices() {
        viewModelScope.launch(Dispatchers.IO) {
            val services = serviceDao.getAllServicesSync()
            _allServices.postValue(services)
        }
    }

    fun insertService(carId: Long, service: Service) {
        viewModelScope.launch(Dispatchers.IO) {
            val car = carDao.getCarById(carId)
            if (car != null) {
                val serviceId = serviceDao.insertService(service)
                firebaseService.addServiceToFirebase(service.copy(id = serviceId))
                loadServices()
            } else {
                Log.e("ServiceViewModel", "Car with id $carId does not exist")
            }
        }
    }

    fun updateService(service: Service) {
        viewModelScope.launch(Dispatchers.IO) {
            serviceDao.updateService(service)
            firebaseService.updateServiceInFirebase(service.id.toString(), service)
            loadServices()
        }
    }

    fun deleteService(service: Service) {
        viewModelScope.launch(Dispatchers.IO) {
            serviceDao.deleteService(service)
            firebaseService.deleteServiceFromFirebase(service.id.toString())
            loadServices()
        }
    }

}
