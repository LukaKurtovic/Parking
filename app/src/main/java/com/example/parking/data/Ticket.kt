package com.example.parking.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val zone: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    val timestampFormatted: String
        get() = DateFormat.getDateTimeInstance().format(timestamp)
}