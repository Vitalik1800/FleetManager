package com.example.fleetmanager

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetmanager.databinding.ActivityTripBinding

class TripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTripBinding
    private val tripViewModel : TripViewModel by viewModels()
    private val carViewModel : CarViewModel by viewModels()
    private var selectedCarId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carViewModel.allCars.observe(this) { cars ->
            cars?.let {
                setupCarSpinner(it)
            }
        }

        val tripAdapter = TripAdapter()
        binding.recyclerViewTrips.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTrips.adapter = tripAdapter

        tripViewModel.allTrips.observe(this) { trips ->
            carViewModel.allCars.observe(this) { cars ->
                val tripWithCars = trips.mapNotNull { trip ->
                    val car = cars.find { it.id == trip.carId }
                    car?.let { trip to it }
                }
                tripAdapter.submitList(tripWithCars)
            }
        }


        binding.buttonAddTrip.setOnClickListener {
            val date = binding.editTextDate.text.toString()
            val distance = binding.editTextDistance.text.toString().toDoubleOrNull()
            val fuelConsumption = binding.editTextFuelConsumption.text.toString().toDoubleOrNull()

            if(date.isNotEmpty() && distance != null && fuelConsumption != null){
                val trip = Trip(carId = selectedCarId, date = date, distance = distance, fuelConsumption = fuelConsumption)
                tripViewModel.insertTrip(selectedCarId, trip)
                Toast.makeText(this, "Trip added", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonUpdateTrip.setOnClickListener {
            val date = binding.editTextDate.text.toString()
            val distance = binding.editTextDistance.text.toString().toDoubleOrNull()
            val fuelConsumption = binding.editTextFuelConsumption.text.toString().toDoubleOrNull()

            val selectedTrip = tripAdapter.getSelectedTrip()

            if (selectedTrip != null && date.isNotEmpty() && distance != null && fuelConsumption != null) {
                val updatedTrip = selectedTrip.copy(carId = selectedCarId, date = date, distance = distance, fuelConsumption = fuelConsumption)
                tripViewModel.updateTrip(updatedTrip)
                Toast.makeText(this, "Trip updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a trip to update", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDeleteTrip.setOnClickListener {
            val selectedTrip = tripAdapter.getSelectedTrip()

            if (selectedTrip != null) {
                tripViewModel.deleteTrip(selectedTrip)
                Toast.makeText(this, "Trip deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a trip to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCarSpinner(cars: List<Car>){
        val carModels = cars.map { it.model }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carModels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCar.adapter = adapter

        binding.spinnerCar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedCarId = cars[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }

        }
    }
}