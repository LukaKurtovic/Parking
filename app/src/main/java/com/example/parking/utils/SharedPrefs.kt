package com.example.parking.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefs @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun addLicence(licence: String){
        editor.apply{
            putString("LICENCE", licence)
        }.commit()
    }

    fun getLicence(): String = preferences.getString("LICENCE", "")!!
}