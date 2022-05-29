package com.example.parking.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticket: Ticket)

    @Query("SELECT * FROM Ticket ORDER BY timestamp DESC")
    fun getAllTickets(): LiveData<List<Ticket>>

}