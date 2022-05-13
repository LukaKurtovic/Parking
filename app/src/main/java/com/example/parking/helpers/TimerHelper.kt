package com.example.parking.helpers

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerHelper @Inject constructor() {
    val timeLeft = MutableStateFlow(0L)

    val countDownTimer = object : CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            timeLeft.value = millisUntilFinished / 60000
        }

        override fun onFinish() {
            return
        }
    }
}

private const val MILLIS_IN_FUTURE = 3600000L
private const val COUNT_DOWN_INTERVAL = 1000L