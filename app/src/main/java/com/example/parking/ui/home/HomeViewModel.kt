package com.example.parking.ui.home

import androidx.lifecycle.*
import com.example.parking.data.*
import com.example.parking.helpers.LocationHelper
import com.example.parking.helpers.SmsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    locationHelper: LocationHelper,
    private val smsHelper: SmsHelper
) : ViewModel() {
    val locationListener = locationHelper.locationListener

    val ticketData = combine(
        locationHelper.mutableAddress.asFlow(),
        locationHelper.mutableZone.asFlow(),
        locationHelper.mutableIsLocationInZone.asFlow()
    ) { address, zone, isInZone ->
        Triple(address, zone, isInZone)
    }.asLiveData()

    fun onConfirmClick(ticket: Ticket) = viewModelScope.launch {
        ticketRepository.insert(ticket)
    }

    fun sendSMS(zone: String) = smsHelper.sendSMS(zone)
}