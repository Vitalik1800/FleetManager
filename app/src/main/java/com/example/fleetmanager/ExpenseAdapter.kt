package com.example.fleetmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetmanager.databinding.ItemExpenseBinding

class ExpenseAdapter : ListAdapter<Pair<Expense, Car>, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()){

    private var selectedExpense : Expense? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val(expense, car) = getItem(position)
        holder.bind(expense, car)
    }

    fun getSelectedExpense() : Expense? {
        return selectedExpense
    }

    inner class ExpenseViewHolder(private val binding: ItemExpenseBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                selectedExpense = getItem(adapterPosition).first
            }
        }

        fun bind(expense: Expense, car: Car){
            binding.expense = expense
            binding.car = car
            binding.executePendingBindings()
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Pair<Expense, Car>>(){
        override fun areItemsTheSame(oldItem: Pair<Expense, Car>, newItem: Pair<Expense, Car>): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(oldItem: Pair<Expense, Car>, newItem: Pair<Expense, Car>): Boolean {
            return oldItem == newItem
        }
    }

}