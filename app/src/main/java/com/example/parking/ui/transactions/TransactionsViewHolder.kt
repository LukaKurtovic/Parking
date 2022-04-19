package com.example.parking.ui.transactions

import androidx.recyclerview.widget.RecyclerView
import com.example.parking.data.Ticket
import com.example.parking.databinding.ItemTicketBinding

class TransactionsViewHolder(private val binding: ItemTicketBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ticket: Ticket) {
        binding.tvZone.text = "Zone ${ticket.zone}"
        binding.tvTimestamp.text = ticket.timestampFormatted
    }
}