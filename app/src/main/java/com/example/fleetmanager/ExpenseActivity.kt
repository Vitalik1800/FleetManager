package com.example.fleetmanager

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetmanager.databinding.ActivityExpenseBinding

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding
    private val expenseViewModel : ExpenseViewModel by viewModels()
    private val carViewModel : CarViewModel by viewModels()
    private var selectedCarId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carViewModel.allCars.observe(this){cars ->
            cars?.let {
                setupCarSpinner(it)
            }
        }

        val expenseAdapter = ExpenseAdapter()
        binding.recyclerViewExpenses.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewExpenses.adapter = expenseAdapter

        expenseViewModel.allExpenses.observe(this){ expenses ->
            carViewModel.allCars.observe(this){ cars ->
                val expensesWithCars = expenses.mapNotNull { expense ->
                    val car = cars.find { it.id == expense.carId }
                    car?.let { expense to it }
                }
                expenseAdapter.submitList(expensesWithCars)
            }
        }

        binding.buttonAddExpense.setOnClickListener {
            val expenseDate = binding.editTextDate.text.toString()
            val description = binding.editTextDescription.text.toString()
            val amount = binding.editTextAmount.text.toString().toDoubleOrNull()

            if(expenseDate.isNotEmpty() && description.isNotEmpty() && amount != null){
                val expense = Expense(carId = selectedCarId, expenseDate = expenseDate, description = description, amount = amount)
                expenseViewModel.insertExpense(selectedCarId, expense)
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonUpdateExpense.setOnClickListener {
            val date = binding.editTextDate.text.toString()
            val description = binding.editTextDescription.text.toString()
            val amount = binding.editTextAmount.text.toString().toDoubleOrNull()

            val selectedExpense = expenseAdapter.getSelectedExpense()

            if (selectedExpense != null && date.isNotEmpty() && description.isNotEmpty() && amount != null) {
                val updatedExpense = selectedExpense.copy(carId = selectedCarId, expenseDate = date, description = description, amount = amount)
                expenseViewModel.updateExpense(updatedExpense)
                Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a expense to update", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDeleteExpense.setOnClickListener {
            val selectedExpense = expenseAdapter.getSelectedExpense()

            if (selectedExpense != null) {
                expenseViewModel.deleteExpense(selectedExpense)
                Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a expense to delete", Toast.LENGTH_SHORT).show()
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