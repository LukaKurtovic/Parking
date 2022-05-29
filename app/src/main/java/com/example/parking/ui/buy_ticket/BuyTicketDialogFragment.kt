package com.example.parking.ui.buy_ticket

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.parking.R
import com.example.parking.data.Ticket
import com.example.parking.helpers.ActivityResultCallback
import com.example.parking.helpers.PermissionHelper
import com.example.parking.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuyTicketDialogFragment : DialogFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val args: BuyTicketDialogFragmentArgs by navArgs()
    @Inject lateinit var permissionHelper: PermissionHelper

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message) + " " + args.zone)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes") { _, _ ->
                onConfirm()
            }
            .create()

    private fun onConfirm() {
        viewModel.onConfirmClick(Ticket(zone = args.zone))
        viewModel.sendSMS(args.zone)
        Toast.makeText(requireContext(), getString(R.string.ticket_bought_messsage), Toast.LENGTH_SHORT)
            .show()
    }
}