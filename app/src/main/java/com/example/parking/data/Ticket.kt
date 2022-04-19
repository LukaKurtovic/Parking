package com.example.parking.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat


@Entity
data class Ticket(
    val zone: String,
    val timestamp: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    val timestampFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timestamp)
}