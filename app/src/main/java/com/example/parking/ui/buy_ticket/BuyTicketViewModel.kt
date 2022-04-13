package com.example.parking.ui.buy_ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parking.data.Ticket
import com.example.parking.data.TicketDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyTicketViewModel @Inject constructor(
    private val ticketDao: TicketDao
) : ViewModel() {

    fun onConfirmClick(ticket: Ticket) = viewModelScope.launch {
        ticketDao.insert(ticket)
    }
}