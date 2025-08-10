package com.example.fleetmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetmanager.databinding.ItemCarBinding

class CarAdapter : ListAdapter<Car, CarAdapter.CarViewHolder>(CarDiffCallback()) {

    private var selectedCar : Car? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind(car)
    }

    fun getSelectedCar(): Car?{
        return selectedCar
    }

    inner class CarViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                selectedCar = getItem(adapterPosition)
            }
        }

        fun bind(car: Car){
            binding.car = car
            binding.executePendingBindings()
        }
    }

    class CarDiffCallback : DiffUtil.ItemCallback<Car>(){
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }
    }
}