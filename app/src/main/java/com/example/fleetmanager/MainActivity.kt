package com.example.fleetmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fleetmanager.databinding.ActivityMainBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addCarButton.setOnClickListener {
            startActivity(Intent(this, CarsActivity::class.java))
        }
        binding.addTripButton.setOnClickListener {
            startActivity(Intent(this, TripActivity::class.java))
        }
        binding.addServiceButton.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }
        binding.addExpenseButton.setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
        }

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(360000)
            .build()

        mFirebaseRemoteConfig.setConfigSettingsAsync(settings)

        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("MainActivity", "Fetch and activate succeeded: $updated")
                    Toast.makeText(this, "Remote config updated!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("MainActivity", "Fetch failed")
                    Toast.makeText(this, "Failed to fetch remote config", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
