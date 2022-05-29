package com.example.parking

import android.app.Application
import android.content.Context
import com.example.parking.ui.MainActivity
import com.example.parking.utils.SharedPrefs
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class App : Application()