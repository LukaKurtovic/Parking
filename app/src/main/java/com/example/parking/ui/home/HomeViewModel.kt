package com.example.parking.ui.home

import androidx.lifecycle.*
import com.example.parking.data.*
import com.example.parking.helpers.*
import com.example.parking.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val locationHelper: LocationHelper,
    private val timerHelper: TimerHelper,
    private val smsHelper: SmsHelper,
    private val prefs: SharedPrefs,
    private val alarmHelper: AlarmHelper
) : ViewModel() {
    val locationInfo = locationHelper.locationInfo.asLiveData()
    val timeLeft = timerHelper.timeLeft.asLiveData()

    fun onConfirmClick(ticket: Ticket) {
        viewModelScope.launch {
            ticketRepository.insert(ticket)
        }
        timerHelper.countDownTimer.start()
        alarmHelper.setAlarm(System.currentTimeMillis() + 10000)
    }

    fun sendSMS(zone: String) {
        smsHelper.sendSMS(zone)
        prefs.addLastTicket(zone)
    }

    fun getLicence(): String = prefs.getLicence()

    fun getLastTicket(): String = prefs.getLastTicket()

    fun fetchLocation() = locationHelper.fetchLocation()
}
