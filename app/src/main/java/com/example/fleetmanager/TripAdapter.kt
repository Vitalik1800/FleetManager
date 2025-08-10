package com.example.fleetmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fleetmanager.databinding.ItemTripBinding

class TripAdapter : ListAdapter<Pair<Trip, Car>, TripAdapter.TripViewHolder>(TripDiffCallback()) {

    private var selectedTrip: Trip? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val (trip, car) = getItem(position)
        holder.bind(trip, car)
    }

    fun getSelectedTrip(): Trip? {
        return selectedTrip
    }

    inner class TripViewHolder(private val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                selectedTrip = getItem(adapterPosition).first
            }
        }

        fun bind(trip: Trip, car: Car) {
            binding.trip = trip
            binding.car = car
            binding.executePendingBindings()
        }
    }

    class TripDiffCallback : DiffUtil.ItemCallback<Pair<Trip, Car>>() {
        override fun areItemsTheSame(oldItem: Pair<Trip, Car>, newItem: Pair<Trip, Car>): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(oldItem: Pair<Trip, Car>, newItem: Pair<Trip, Car>): Boolean {
            return oldItem == newItem
        }
    }
}
