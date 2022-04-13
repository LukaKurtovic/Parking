package com.example.parking.ui.transactions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parking.R
import com.example.parking.databinding.FragmentTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    val viewModel: TransactionsViewModel by viewModels()

    private lateinit var binding: FragmentTransactionsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTransactionsBinding.bind(view)

        val transactionsAdapter = TransactionsAdapter()

        binding.apply {
            rvTransactions.apply {
                adapter = transactionsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.tickets.observe(viewLifecycleOwner){
            transactionsAdapter.submitList(it)
        }

    }
}