package com.example.parking.ui.buy_ticket

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.parking.data.Ticket
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyTicketDialogFragment : DialogFragment() {

    private val viewModel: BuyTicketViewModel by viewModels()
    private val args: BuyTicketDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure?")
            .setMessage("You are buying a parking ticket for zone ${args.zone}")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes"){_,_ ->
                viewModel.onConfirmClick(Ticket(args.zone))
            }
            .create()

}