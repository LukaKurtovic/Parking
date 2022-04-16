package com.example.parking.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Ticket::class], version = 1)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getTicketDao(): TicketDao
}