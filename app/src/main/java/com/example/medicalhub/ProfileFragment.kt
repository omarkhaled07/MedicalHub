package com.example.medicalhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var preferences: SharedPrefManager
    private lateinit var tvName: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = SharedPrefManager(requireContext())

        binding.btnPersonalInfo.setOnClickListener {
            val selectedRadioButton =
                preferences.getPrefVal(requireContext()).getString("SelectedRadioButton", "")
                    .toString()

            if (selectedRadioButton == "Doctor") {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            } else {
                findNavController().navigate(R.id.action_profileFragment_to_editProfilePatient)
            }

        }

        binding.tvName.text = preferences.getPrefVal(requireContext()).getString("username", "")
    }
}