package com.example.parking.ui.transactions

import androidx.lifecycle.ViewModel
import com.example.parking.data.TicketDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    ticketDao: TicketDao
) : ViewModel() {

    val tickets = ticketDao.getAllTickets()
}