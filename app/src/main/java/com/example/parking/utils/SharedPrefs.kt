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

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun addLicence(licence: String) {
        editor.apply {
            putString(LICENCE, licence)
        }.commit()
    }

    fun getLicence(): String = preferences.getString(LICENCE, "") ?: ""

    fun addLastTicket(zone: String) {
        editor.apply {
            putString(LAST_TICKET, zone)
        }.commit()
    }

    fun addTimeToFinish(time: Long) {
        editor.apply {
            putLong(TIME_TO_FINISH, time)
        }.commit()
    }

    fun getTimeToFinish(): Long = preferences.getLong(TIME_TO_FINISH, 0L)

}

private const val PREFERENCES = "preferences"
private const val LICENCE = "licence"
private const val LAST_TICKET = "last_ticket"
private const val TIME_TO_FINISH = "time_to_finish"
