package com.example.fleetmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetmanager.databinding.ItemServiceBinding

class ServiceAdapter : ListAdapter<Pair<Service, Car>, ServiceAdapter.ServiceViewHolder>(ServiceDiffCallback()){

    private var selectedService : Service? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val(service, car) = getItem(position)
        holder.bind(service, car)
    }

    fun getSelectedService() : Service? {
        return selectedService
    }

    inner class ServiceViewHolder(private val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                selectedService = getItem(adapterPosition).first
            }
        }

        fun bind(service: Service, car: Car){
            binding.service = service
            binding.car = car
            binding.executePendingBindings()
        }
    }

    class ServiceDiffCallback : DiffUtil.ItemCallback<Pair<Service, Car>>(){
        override fun areItemsTheSame(oldItem: Pair<Service, Car>, newItem: Pair<Service, Car>): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(oldItem: Pair<Service, Car>, newItem: Pair<Service, Car>): Boolean {
            return oldItem == newItem
        }
    }

}