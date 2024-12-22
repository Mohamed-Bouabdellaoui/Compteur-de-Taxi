package com.example.taxiv2

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FirstFragment : Fragment(R.layout.fragment_first), OnMapReadyCallback {

    private val baseFare: Double = 2.5
    private val farePerKm: Double = 1.5
    private val farePerMinute: Double = 0.5

    private var totalFare: Double = 0.0
    private var distanceTraveled: Double = 0.0
    private var elapsedTimeInMinutes: Double = 0.0
    private var startTime: Long = 0
    private var previousLocation: Location? = null
    private var isRideActive: Boolean = false

    private lateinit var mMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        view.findViewById<Button>(R.id.btnStartRide)?.setOnClickListener { button ->
            if (isRideActive) {
                stopRide()
                isRideActive = false
                (button as Button).text = getString(R.string.start_ride)
            } else {
                startRide()
                isRideActive = true
                (button as Button).text = getString(R.string.stop_ride)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val defaultLocation = LatLng(33.5731, -7.5898)
        mMap.addMarker(MarkerOptions().position(defaultLocation).title("Default Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
    }

    private fun calculateFare() {
        elapsedTimeInMinutes = (System.currentTimeMillis() - startTime) / 60000.0
        totalFare = baseFare + (distanceTraveled * farePerKm) + (elapsedTimeInMinutes * farePerMinute)
        updateFareUI()
    }

    private fun updateFareUI() {
        val distanceText = String.format("Distance: %.2f km", distanceTraveled)
        val timeText = String.format("Time: %.2f min", elapsedTimeInMinutes)
        val fareText = String.format("Fare: %.2f DH", totalFare)

        view?.findViewById<TextView>(R.id.tvDistance)?.text = distanceText
        view?.findViewById<TextView>(R.id.tvTime)?.text = timeText
        view?.findViewById<TextView>(R.id.tvFare)?.text = fareText
    }

    private fun startRide() {
        startTime = System.currentTimeMillis()
        distanceTraveled = 0.0
        totalFare = 0.0
        previousLocation = null

        Toast.makeText(requireContext(), "Ride started!", Toast.LENGTH_SHORT).show()
    }

    private fun updateLocation(location: Location) {
        if (previousLocation != null) {
            distanceTraveled += previousLocation!!.distanceTo(location) / 1000.0
        }
        previousLocation = location
        calculateFare()
    }

    private fun stopRide() {
        calculateFare()
        Toast.makeText(requireContext(), "Ride stopped! Total Fare: ${String.format("%.2f", totalFare)} DH", Toast.LENGTH_LONG).show()
    }
}
