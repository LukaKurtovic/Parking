package com.example.parking.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parking.data.Ticket
import com.example.parking.databinding.ItemTicketBinding

class TransactionsAdapter : ListAdapter<Ticket, TransactionsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionsViewHolder.create(parent)

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket) =
            oldItem == newItem
    }
}