package com.example.parking.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.parking.R
import com.example.parking.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        initUi()
    }

    private fun initUi() = with(binding) {
        btnSave.setOnClickListener {
            viewModel.addLicence(etLicence.text.toString())
            // TODO: Move this check to the viewmodel
            if (etLicence.text.isNotBlank()) {
                viewModel.addLicence(etLicence.text.toString())
                ProfileFragmentDirections.actionGlobalHomeFragment().run {
                    findNavController().navigate(this)
                }
                Toast.makeText(requireContext(), getString(R.string.licence_saved), Toast.LENGTH_SHORT).show()
            } else Toast.makeText(
                requireContext(),
                getString(R.string.licence_empty),
                Toast.LENGTH_SHORT
            ).show()

        }
        etLicence.setText(viewModel.getLicence())
    }

    override fun onResume() {
        super.onResume()
        binding.etLicence.setText(viewModel.getLicence())
    }
}