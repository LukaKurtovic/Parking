package com.example.parking.ui.home

import androidx.lifecycle.*
import com.example.parking.data.*
import com.example.parking.helpers.LocationHelper
import com.example.parking.helpers.SmsHelper
import com.example.parking.helpers.TimerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    locationHelper: LocationHelper,
    private val timerHelper: TimerHelper,
    private val smsHelper: SmsHelper,
) : ViewModel() {
    val locationListener = locationHelper.locationListener
    private val mutableActiveTicketZone = MutableLiveData("")

    val currentLocationData = combine(
        locationHelper.mutableAddress.asFlow(),
        locationHelper.mutableZone.asFlow(),
        locationHelper.mutableIsLocationInZone.asFlow()
    ) { address, zone, isInZone ->
        Triple(address, zone, isInZone)
    }.asLiveData()

    val activeTicketData = combine(
        timerHelper.mutableTimeLeft.asFlow(),
        mutableActiveTicketZone.asFlow()
    ) { timeLeft, activeZone ->
        Pair(timeLeft, activeZone)
    }.asLiveData()

    fun onConfirmClick(ticket: Ticket) = viewModelScope.launch {
        ticketRepository.insert(ticket)
        startTimer()
    }

    fun sendSMS(zone: String) {
        smsHelper.sendSMS(zone)
        mutableActiveTicketZone.value = zone
    }

    private fun startTimer() {
        timerHelper.countDownTimer.start()
    }
}