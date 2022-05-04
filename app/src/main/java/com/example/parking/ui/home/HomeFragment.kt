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
    private val smsPermission = Manifest.permission.SEND_SMS
    private val locationManager by lazy { requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private var zone: String = ""
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        trackLocation()
        binding.btnPay.apply {
            setOnClickListener {
                if (viewModel.getLicence().isNotBlank()) {
                    onPayClicked()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "You have to enter the licence number first",
                        Toast.LENGTH_SHORT
                    ).show()
                    HomeFragmentDirections.actionGlobalProfileFragment().run {
                        findNavController().navigate(this)
                    }
                }
            }
        }

        viewModel.currentLocationData.observe(viewLifecycleOwner) {
            binding.apply {
                "CURRENT LOCATION: ${it.first}".also { tvAddress.text = it }
                "CURRENT ZONE: ${it.second}".also { tvZone.text = it }
                btnPay.isEnabled = it.third
                zone = it.second
            }
        }

        viewModel.activeTicketData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.first != 0L) {
                    "TIME LEFT: ${it.first} seconds left".also { tvTimeLeft.text = it }
                    "YOU HAVE A VALID TICKET FOR ZONE ${it.second}".also { tvActive.text = it }
                } else "TIME LEFT: You don't have a valid ticket".also { tvTimeLeft.text = it }
            }
        }
    }

    private fun trackLocation() {
        if (requireContext().hasPermission(locationPermission)) {
            startTrackingLocation()
        } else {
            permReqLauncher.launch(arrayOf(locationPermission, smsPermission))
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
        HomeFragmentDirections.actionGlobalBuyTicketDialogFragment(zone)
            .run { findNavController().navigate(this) }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(viewModel.locationListener)
    }

    override fun onResume() {
        super.onResume()
        trackLocation()
    }
}