package com.example.parking.ui.home

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.parking.R
import com.example.parking.databinding.FragmentHomeBinding
import com.example.parking.helpers.ActivityResultCallback
import com.example.parking.helpers.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    @Inject lateinit var permissionHelper: PermissionHelper
    private lateinit var binding: FragmentHomeBinding
    private var zone: String = ""
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var notificationManager: NotificationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

        binding.btnPay.setOnClickListener {
            if (viewModel.getLicence().isNotBlank()) {
                onBuyClicked()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.licence_empty),
                    Toast.LENGTH_SHORT
                ).show()
                HomeFragmentDirections.actionGlobalProfileFragment().run {
                    findNavController().navigate(this)
                }
            }
        }

        binding.apply {
            btnLocate.setOnClickListener {
                permissionHelper.withPermission(permissionHelper.locationPermission,
                    { viewModel.fetchLocation() },
                    { locationPermissionLauncher.launch(arrayOf(permissionHelper.locationPermission)) }
                )
                loadingIcon.isVisible = true
            }
        }

        viewModel.locationInfo.observe(viewLifecycleOwner) {
            binding.apply {
                "CURRENT ADDRESS: ${it.address}".also { tvAddress.text = it }
                "CURRENT ZONE: ${it.zone}".also { tvZone.text = it }
                binding.loadingIcon.isVisible = false
                btnPay.isEnabled = it.isInZone
                zone = it.zone
            }
        }

        viewModel.resumeIfRunning()

        viewModel.timeLeft.observe(viewLifecycleOwner) {
            if (it != 0L) binding.tvTimeLeft.text = "TIME LEFT: $it min"
            else binding.tvTimeLeft.text = getString(R.string.no_active_ticket)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ActivityResultCallback({ viewModel.fetchLocation() }, ::showPermissionNotGranted)
    )

    private val smsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ActivityResultCallback({ navigateToBuyTicketDialog() }, ::showPermissionNotGranted)
    )

    private fun showPermissionNotGranted() {
        Toast.makeText(
            requireContext(),
            getString(R.string.permission_denied_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onBuyClicked() {
        permissionHelper.withPermission(permissionHelper.smsPermission,
            { navigateToBuyTicketDialog() },
            { smsPermissionLauncher.launch(arrayOf(permissionHelper.smsPermission)) })

    }

    private fun navigateToBuyTicketDialog() =
        HomeFragmentDirections.actionGlobalBuyTicketDialogFragment(zone)
            .run { findNavController().navigate(this) }
}