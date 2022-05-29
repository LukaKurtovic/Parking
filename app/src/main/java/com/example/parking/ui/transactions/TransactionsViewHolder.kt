package com.example.parking.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parking.data.Ticket
import com.example.parking.databinding.ItemTicketBinding

class TransactionsViewHolder(private val binding: ItemTicketBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(ticket: Ticket) {
        "Zone ${ticket.zone}".also { binding.tvZone.text = it }
        binding.tvTimestamp.text = ticket.timestampFormatted
    }

    companion object {
        fun create(parent: ViewGroup): TransactionsViewHolder {
            val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TransactionsViewHolder(binding)
        }
    }
}