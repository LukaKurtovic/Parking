package com.example.parking.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.parking.R
import com.example.parking.databinding.FragmentProfileBinding
import com.example.parking.utils.SharedPrefs

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val prefs by lazy { SharedPrefs(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.apply {
            btnSave.setOnClickListener {
                if (etLicence.text.isNotBlank()) {
                    prefs.addLicence(etLicence.text.toString())
                    ProfileFragmentDirections.actionGlobalHomeFragment().run {
                        findNavController().navigate(this)
                    }
                    Toast.makeText(requireContext(), "Licence saved", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(requireContext(), "Licence number cannot be empty!", Toast.LENGTH_SHORT).show()

            }
            etLicence.setText(prefs.getLicence())
        }
    }

    override fun onResume() {
        super.onResume()

        binding.etLicence.setText(prefs.getLicence())
    }
}