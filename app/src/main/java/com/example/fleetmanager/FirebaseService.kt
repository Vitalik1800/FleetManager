package com.example.fleetmanager

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseService {
    private val db = FirebaseFirestore.getInstance()

    fun addCarToFirebase(car: Car) {
        val carData = car.copy()
        db.collection("cars").document(car.id.toString()).set(carData)
            .addOnSuccessListener {
                Log.d("FirebaseService", "Car added with ID: ${car.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to add car", e)
            }
    }

    fun updateCarInFirebase(carId: String, updatedCar: Car) {
        val docRef = db.collection("cars").document(carId)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                docRef.update(
                    "model", updatedCar.model,
                    "year", updatedCar.year,
                    "plateNumber", updatedCar.plateNumber
                ).addOnSuccessListener {
                    Log.d("FirebaseService", "Car updated successfully")
                }.addOnFailureListener { e ->
                    Log.e("FirebaseService", "Failed to update car", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("FirebaseService", "Failed to get car document", e)
        }
    }

    fun deleteCarFromFirebase(carId: String) {
        db.collection("cars").document(carId).delete()
            .addOnSuccessListener {
                Log.d("FirebaseService", "Car deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to delete car", e)
            }
    }

    fun addTripToFirebase(trip: Trip) {
        val tripData = trip.copy()
        db.collection("trips").document(trip.id.toString()).set(tripData)
            .addOnSuccessListener {
                Log.d("FirebaseService", "Trip added with ID: ${trip.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to add trip", e)
            }
    }

    fun updateTripInFirebase(tripId: String, updatedTrip: Trip) {
        val docRef = db.collection("trips").document(tripId)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                docRef.update(
                    "carId", updatedTrip.carId,
                    "date", updatedTrip.date,
                    "distance", updatedTrip.distance,
                    "fuelConsumption", updatedTrip.fuelConsumption
                ).addOnSuccessListener {
                    Log.d("FirebaseService", "Trip updated successfully")
                }.addOnFailureListener { e ->
                    Log.e("FirebaseService", "Failed to update trip", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("FirebaseService", "Failed to get car document", e)
        }
    }

    fun deleteTripFromFirebase(tripId: String) {
        db.collection("trips").document(tripId).delete()
            .addOnSuccessListener {
                Log.d("FirebaseService", "Trip deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to delete trip", e)
            }
    }

    fun addServiceToFirebase(service: Service) {
        val serviceData = service.copy()
        db.collection("services").document(service.id.toString()).set(serviceData)
            .addOnSuccessListener {
                Log.d("FirebaseService", "Service added with ID: ${service.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to add service", e)
            }
    }

    fun updateServiceInFirebase(serviceId: String, updatedService: Service) {
        val docRef = db.collection("services").document(serviceId)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                docRef.update(
                    "carId", updatedService.carId,
                    "serviceDate", updatedService.serviceDate,
                    "serviceType", updatedService.serviceType,
                    "cost", updatedService.cost
                ).addOnSuccessListener {
                    Log.d("FirebaseService", "Service updated successfully")
                }.addOnFailureListener { e ->
                    Log.e("FirebaseService", "Failed to update service", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("FirebaseService", "Failed to get service document", e)
        }
    }

    fun deleteServiceFromFirebase(serviceId: String) {
        db.collection("services").document(serviceId).delete()
            .addOnSuccessListener {
                Log.d("FirebaseService", "Service deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to delete service", e)
            }
    }

    fun addExpenseToFirebase(expense: Expense) {
        val expenseData = expense.copy()
        db.collection("expenses").document(expense.id.toString()).set(expenseData)
            .addOnSuccessListener {
                Log.d("FirebaseService", "Expense added with ID: ${expense.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to add expense", e)
            }
    }

    fun updateExpenseInFirebase(expenseId: String, updatedExpense: Expense) {
        val docRef = db.collection("expenses").document(expenseId)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                docRef.update(
                    "carId", updatedExpense.carId,
                    "expenseDate", updatedExpense.expenseDate,
                    "description", updatedExpense.description,
                    "amount", updatedExpense.amount
                ).addOnSuccessListener {
                    Log.d("FirebaseService", "Expense updated successfully")
                }.addOnFailureListener { e ->
                    Log.e("FirebaseService", "Failed to update expense", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("FirebaseService", "Failed to get expense document", e)
        }
    }

    fun deleteExpenseFromFirebase(expenseId: String) {
        db.collection("expenses").document(expenseId).delete()
            .addOnSuccessListener {
                Log.d("FirebaseService", "Expense deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Failed to delete expense", e)
            }
    }

}