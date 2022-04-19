package com.example.parking.ui.transactions

import androidx.lifecycle.ViewModel
import com.example.parking.data.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    ticketRepository: TicketRepository
) : ViewModel() {
    val tickets = ticketRepository.allTickets
}