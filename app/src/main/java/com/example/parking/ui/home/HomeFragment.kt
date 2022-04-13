package com.example.parking.ui.home

import android.Manifest
import android.content.Context
import android.location.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.parking.R
import com.example.parking.databinding.FragmentHomeBinding
import com.example.parking.utils.hasPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val locationManager by lazy { requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private var zone: String = ""
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        trackLocation()

        binding.btnPay.setOnClickListener { onPayClicked() }

        viewModel.address.observe(viewLifecycleOwner){
            binding.tvAddress.text = "CURRENT LOCATION: $it"
        }
        viewModel.zone.observe(viewLifecycleOwner){
            binding.tvZone.text = "CURRENT ZONE: $it"
            zone = it
        }
    }

    private fun trackLocation() {
        if (requireContext().hasPermission(locationPermission)) {
            startTrackingLocation()
        } else {
            permReqLauncher.launch(arrayOf(locationPermission))
        }
    }

    private fun startTrackingLocation() {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE

        val provider = locationManager.getBestProvider(criteria, true)
        val minTime = 1000L
        val minDistance = 1.0f
        try {
            locationManager.requestLocationUpdates(
                provider!!,
                minTime,
                minDistance,
                viewModel.locationListener
            )
        } catch (e: SecurityException) {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            val granted = permission.entries.all {
                it.value == true
            }
            if (granted) {
                startTrackingLocation()
            } else Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }

    private fun onPayClicked() {
        val action = HomeFragmentDirections.actionGlobalBuyTicketDialogFragment(zone)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(viewModel.locationListener)
    }

}