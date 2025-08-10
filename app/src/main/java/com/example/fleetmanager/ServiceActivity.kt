package com.example.fleetmanager

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetmanager.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private val serviceViewModel : ServiceViewModel by viewModels()
    private val carViewModel : CarViewModel by viewModels()
    private var selectedCarId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carViewModel.allCars.observe(this){cars ->
            cars?.let {
                setupCarSpinner(it)
            }
        }

        val serviceAdapter = ServiceAdapter()
        binding.recyclerViewServices.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewServices.adapter = serviceAdapter

        serviceViewModel.allServices.observe(this){ services ->
            carViewModel.allCars.observe(this){ cars ->
                val servicesWithCars = services.mapNotNull { service ->
                    val car = cars.find { it.id == service.carId }
                    car?.let { service to it }
                }
                serviceAdapter.submitList(servicesWithCars)
            }
        }

        binding.buttonAddService.setOnClickListener {
            val serviceDate = binding.editTextDate.text.toString()
            val serviceType = binding.editTextServiceType.text.toString()
            val cost = binding.editTextCost.text.toString().toDoubleOrNull()

            if(serviceDate.isNotEmpty() && serviceType.isNotEmpty() && cost != null){
                val service = Service(carId = selectedCarId, serviceDate = serviceDate, serviceType = serviceType, cost = cost)
                serviceViewModel.insertService(selectedCarId, service)
                Toast.makeText(this, "Service added", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonUpdateService.setOnClickListener {
            val date = binding.editTextDate.text.toString()
            val serviceType = binding.editTextServiceType.text.toString()
            val cost = binding.editTextCost.text.toString().toDoubleOrNull()

            val selectedService = serviceAdapter.getSelectedService()

            if (selectedService != null && date.isNotEmpty() && serviceType.isNotEmpty() && cost != null) {
                val updatedService = selectedService.copy(carId = selectedCarId, serviceDate = date, serviceType = serviceType, cost = cost)
                serviceViewModel.updateService(updatedService)
                Toast.makeText(this, "Service updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a service to update", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDeleteService.setOnClickListener {
            val selectedService = serviceAdapter.getSelectedService()

            if (selectedService != null) {
                serviceViewModel.deleteService(selectedService)
                Toast.makeText(this, "Service deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a service to delete", Toast.LENGTH_SHORT).show()
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