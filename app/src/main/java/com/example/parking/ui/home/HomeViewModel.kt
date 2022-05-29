package com.example.parking.ui.home

import androidx.lifecycle.*
import com.example.parking.data.*
import com.example.parking.helpers.*
import com.example.parking.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

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
        alarmHelper.setAlarm(System.currentTimeMillis() + 10000)
        prefs.addTimeToFinish(System.currentTimeMillis() + 3600000)
        startTimer()

    }

    private fun startTimer() {
        if (timerHelper.countDownTimer != null) stopTimer()
        val millisInFuture = when (prefs.getTimeToFinish()) {
            0L -> ONE_HOUR
            else -> prefs.getTimeToFinish() - System.currentTimeMillis()
        }

        timerHelper.startTimer(millisInFuture)
    }

    fun sendSMS(zone: String) {
        smsHelper.sendSMS(zone)
        prefs.addLastTicket(zone)
    }

    fun getLicence(): String = prefs.getLicence()

    fun fetchLocation() = locationHelper.fetchLocation()

    fun resumeIfRunning() {
        if (prefs.getTimeToFinish() != 0L) startTimer()
    }

    fun stopTimer() {
        timerHelper.stopTimer()
    }

}

private const val ONE_HOUR = 3600000L
