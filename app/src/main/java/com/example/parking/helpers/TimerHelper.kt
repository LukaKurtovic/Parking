package com.example.parking.helpers

import android.os.CountDownTimer
import com.example.parking.App
import com.example.parking.ui.MainActivity
import com.example.parking.utils.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class TimerHelper @Inject constructor(
    private val prefs: SharedPrefs
) {
    val timeLeft = MutableStateFlow(0L)
    var countDownTimer: CountDownTimer? = null

    fun startTimer(millisInFuture: Long) {
        countDownTimer =
            object : CountDownTimer(millisInFuture, COUNT_DOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft.value = millisUntilFinished / 1000 / 60
                }

                override fun onFinish() {
                    prefs.addTimeToFinish(0L)
                }
            }.start()
    }

    fun stopTimer() = countDownTimer?.cancel()
}

const val COUNT_DOWN_INTERVAL = 1000L