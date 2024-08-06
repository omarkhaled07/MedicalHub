package com.example.medicalhub.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicalhub.presentations.HomeActivity
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.repository.Repository
import com.google.android.material.textfield.TextInputEditText
import com.model.DoctorNewData


class EditProfileFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    private lateinit var username: TextInputEditText
    private lateinit var et_specialization: TextInputEditText
    private lateinit var et_email: TextInputEditText
    private lateinit var et_address: TextInputEditText
    private lateinit var et_phoneNumber: TextInputEditText
    private lateinit var et_confirmPassword: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var updateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        preference = SharedPrefManager(requireContext())

        val docName = preference.getPrefVal(requireContext()).getString("username", "")
        Log.d("abdo", "docName $docName")
        val token = preference.getPrefVal(requireContext()).getString("token", "")

        username = view.findViewById(R.id.et_username)
        et_specialization = view.findViewById(R.id.et_specialization)
        et_email = view.findViewById(R.id.et_email)
        et_address = view.findViewById(R.id.et_address)
        et_phoneNumber = view.findViewById(R.id.et_phoneNumber)
        et_confirmPassword = view.findViewById(R.id.et_confirmPassword)
        password = view.findViewById(R.id.et_password)
        updateButton = view.findViewById(R.id.btn_update)

        Log.d("abdo", "Login doctor edit fragment")
        Log.d("abdo", "docName $docName")
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.getDoctorData(docName!!, "Bearer $token")
        viewModel.getDocData.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("abdo", " ${response.body()?.userName.toString()}")
                username.setText(response.body()?.userName.toString())
                et_specialization.setText(response.body()?.specialization.toString())
                et_email.setText(response.body()?.email.toString())
                et_address.setText(response.body()?.address.toString())
                et_phoneNumber.setText(response.body()?.phoneNumber.toString())

            }

        })


        // Set click listener for update button
        updateButton.setOnClickListener {
            val doctorNewData = DoctorNewData(
                userName = username.text.toString(),
                specialization = et_specialization.text.toString(),
                email = et_email.text.toString(),
                address = et_address.text.toString(),
                phone = et_phoneNumber.text.toString(),
                password = password.text.toString(),
                confirmPassword = et_confirmPassword.text.toString(),
            )
            Log.d("abdo", "doctorNewData $doctorNewData")
            if (password.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter a Password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (et_confirmPassword.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please Confirm the Password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (et_phoneNumber.toString().isEmpty() || et_address.toString()
                    .isEmpty() || et_email.toString().isEmpty() || et_specialization.toString()
                    .isEmpty() || username.toString().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please Enter all Fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (password.toString().isEmpty() != et_confirmPassword.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Password Not Match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                viewModel.EditDoctorData(docName, doctorNewData, "Bearer $token")
                viewModel.editDocData.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                        Log.d("abdo", " ${response.body()?.userName.toString()}")
                        startActivity(Intent(requireContext(), HomeActivity::class.java))

                    }

                })
            }


        }

        return view
    }
}