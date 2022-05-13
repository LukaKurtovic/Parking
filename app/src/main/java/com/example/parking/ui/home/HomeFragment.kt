package com.example.parking.ui.home

import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.parking.R
import com.example.parking.data.Ticket
import com.example.parking.databinding.FragmentHomeBinding
import com.example.parking.helpers.ActivityResultCallback
import com.example.parking.helpers.EXTEND_KEY
import com.example.parking.helpers.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    @Inject lateinit var permissionHelper: PermissionHelper
    private lateinit var binding: FragmentHomeBinding
    private var zone: String = ""
    private val viewModel: HomeViewModel by viewModels()
    @Inject lateinit var notificationManager: NotificationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

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

        binding.btnLocate.setOnClickListener {
            permissionHelper.withPermission(permissionHelper.locationPermission,
                { viewModel.fetchLocation() },
                { locationPermissionLauncher.launch(arrayOf(permissionHelper.locationPermission)) }
            )
        }

        viewModel.locationInfo.observe(viewLifecycleOwner) {
            binding.apply {
                "CURRENT ADDRESS: ${it.address}".also { tvAddress.text = it }
                "CURRENT ZONE: ${it.zone}".also { tvZone.text = it }
                btnPay.isEnabled = it.isInZone
                zone = it.zone

            }
        }

        viewModel.timeLeft.observe(viewLifecycleOwner) {
            binding.tvTimeLeft.text = if (it != 0L) {
                "TIME LEFT: $it min"
            } else getString(R.string.no_active_ticket)
        }
        if (activity?.intent?.hasExtra(EXTEND_KEY) == true) {
            onExtendClicked()
        }

    }

    private fun onExtendClicked() {
        viewModel.onConfirmClick(Ticket(zone = viewModel.getLastTicket()))
        viewModel.sendSMS(viewModel.getLastTicket())
        Toast.makeText(requireContext(), "Ticket is extended!", Toast.LENGTH_SHORT).show()
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