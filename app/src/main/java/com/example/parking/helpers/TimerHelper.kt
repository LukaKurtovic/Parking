package com.example.parking.helpers

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class TimerHelper @Inject constructor() {
    val mutableTimeLeft = MutableLiveData(0L)

    val countDownTimer = object : CountDownTimer(10000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            mutableTimeLeft.value = millisUntilFinished / 1000
        }

        override fun onFinish() {
            return
        }

    }
}