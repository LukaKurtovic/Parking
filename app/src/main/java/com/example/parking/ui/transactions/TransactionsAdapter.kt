package com.example.parking.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parking.data.Ticket
import com.example.parking.databinding.ItemTicketBinding

class TransactionsAdapter : ListAdapter<Ticket, TransactionsAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: Ticket){
            binding.tvZone.text = ticket.zone
            binding.tvTimestamp.text = ticket.timestampFormatted
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Ticket>(){
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket) =
            oldItem == newItem

    }
}