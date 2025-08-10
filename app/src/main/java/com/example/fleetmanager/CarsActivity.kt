package com.example.fleetmanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetmanager.databinding.ActivityCarsBinding

class CarsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarsBinding
    private val carViewModel: CarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val carAdapter = CarAdapter()
        binding.recyclerViewCars.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCars.adapter = carAdapter

        carViewModel.allCars.observe(this) { cars ->
            cars?.let { carAdapter.submitList(it) }
        }

        binding.buttonAddCar.setOnClickListener {
            val model = binding.editTextModel.text.toString()
            val year = binding.editTextYear.text.toString().toIntOrNull()
            val plateNumber = binding.editTextPlateNumber.text.toString()

            if(model.isNotEmpty() && year != null && plateNumber.isNotEmpty()){
                val car = Car(model = model, year = year, plateNumber = plateNumber)
                carViewModel.insertCar(car)
                Toast.makeText(this, "Авто додано", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Будь ласка, заповніть усі поля", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonUpdateCar.setOnClickListener {
            val model = binding.editTextModel.text.toString()
            val year = binding.editTextYear.text.toString().toIntOrNull()
            val plateNumber = binding.editTextPlateNumber.text.toString()
            val selectedCar = carAdapter.getSelectedCar()

            if(selectedCar != null && model.isNotEmpty() && year != null && plateNumber.isNotEmpty()){
                val car = selectedCar.copy(model = model, year = year, plateNumber = plateNumber)
                carViewModel.updateCar(car)
                Toast.makeText(this, "Авто оновлено", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Будь ласка, заповніть усі поля та виберіть авто для редагування", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDeleteCar.setOnClickListener {
            val selectedCar = carAdapter.getSelectedCar()

            if(selectedCar != null){
                carViewModel.deleteCar(selectedCar)
                Toast.makeText(this, "Авто видалено", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Будь ласка, виберіть авто для видалення", Toast.LENGTH_SHORT).show()
            }
        }
    }
}