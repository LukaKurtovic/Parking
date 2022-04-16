package com.example.parking.data

import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao
) {
    val allTickets = ticketDao.getAllTickets()

    suspend fun insert(ticket: Ticket) {
        ticketDao.insert(ticket)
    }
}