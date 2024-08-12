package com.example.medicalhub.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.presentations.HomePatientActivity
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.repository.Repository
import com.google.android.material.textfield.TextInputEditText
import com.model.PatientNewData


class EditProfilePatient : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    private lateinit var username: TextInputEditText
    private lateinit var et_NationalID: TextInputEditText
    private lateinit var et_email: TextInputEditText
    private lateinit var et_address: TextInputEditText
    private lateinit var et_phoneNumber: TextInputEditText
    private lateinit var et_confirmPassword: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var tvPatientAdditionalInfo: TextView
    var chronicDiseases: String? = ""
    var previousOperations: String? = ""
    var allergies: String? = ""
    var currentMedications: String? = ""
    var comments: String? = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_edit_profile_patient, container, false)

        preference = SharedPrefManager(requireContext())

        val patID = preference.getPrefVal(requireContext()).getString("patID", "")
        val token = preference.getPrefVal(requireContext()).getString("tokenPatient", "")



        username = view.findViewById(R.id.et_username)
        et_NationalID = view.findViewById(R.id.et_nationalId)
        et_email = view.findViewById(R.id.et_email)
        et_address = view.findViewById(R.id.et_address)
        et_phoneNumber = view.findViewById(R.id.et_phoneNumber)
        et_confirmPassword = view.findViewById(R.id.et_confirmPassword)
        password = view.findViewById(R.id.et_password)
        updateButton = view.findViewById(R.id.btn_update)
        tvPatientAdditionalInfo = view.findViewById(R.id.tvPatientAdditionalInfo)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        Log.d("abdo","Login patient edit fragment")

        tvPatientAdditionalInfo.setOnClickListener {
            showAdditionalInfoDialog()
        }

        viewModel.getPatientData(patID!!, "Bearer $token")
        viewModel.getPatData.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {

                username.setText(response.body()?.userName.toString())
                et_NationalID.setText(response.body()?.nationalID.toString())
                et_email.setText(response.body()?.email.toString())
                et_address.setText(response.body()?.address.toString())
                et_phoneNumber.setText(response.body()?.phoneNumber.toString())
                chronicDiseases=response.body()?.chronicDiseases.toString() ?: ""
                previousOperations=response.body()?.previousOperations.toString() ?: ""
                allergies=response.body()?.allergies.toString() ?: ""
                currentMedications=response.body()?.currentMedications.toString() ?: ""
                comments=response.body()?.comments.toString() ?: ""

            }
        })

        // Set click listener for update button
        updateButton.setOnClickListener {
            val patientNewData = PatientNewData(
                userName = username.text.toString(),
                nationalID = et_NationalID.text.toString(),
                email = et_email.text.toString(),
                address = et_address.text.toString(),
                phone = et_phoneNumber.text.toString(),
                password = password.text.toString(),
                confirmPassword = et_confirmPassword.text.toString(),
            )
            Log.d("abdo", "doctorNewData $patientNewData")
            if (password.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter a Password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (et_confirmPassword.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please Confirm the Password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (et_phoneNumber.toString().isEmpty() || et_address.toString()
                    .isEmpty() || et_email.toString().isEmpty() || et_NationalID.toString()
                    .isEmpty() || username.toString().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please Enter all Fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (password.toString().isEmpty() != et_confirmPassword.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Password Not Match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                viewModel.EditPatientData(patID, patientNewData, "Bearer $token")
                viewModel.editPatData.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                        Log.d("abdo", " ${response.body()?.userName.toString()}")
                        startActivity(Intent(requireContext(), HomePatientActivity::class.java))

                    }

                })
            }


        }

        return view

    }

    private fun showAdditionalInfoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_addtional_info_sign_up, null)
        val etChronicDiseases = dialogView.findViewById<EditText>(R.id.etChronicDiseases)
        val etPreviousOperations = dialogView.findViewById<EditText>(R.id.etPreviousOperations)
        val etAllergies = dialogView.findViewById<EditText>(R.id.etAllergies)
        val etCurrentMedications = dialogView.findViewById<EditText>(R.id.etCurrentMedications)
        val etComments = dialogView.findViewById<EditText>(R.id.etComments)

        // Populate the fields with existing data
        etChronicDiseases.setText(chronicDiseases)
        etPreviousOperations.setText(previousOperations)
        etAllergies.setText(allergies)
        etCurrentMedications.setText(currentMedications)
        etComments.setText(comments)


        AlertDialog.Builder(requireContext())
            .setTitle("Add Additional Info")
            .setView(dialogView)
            .setPositiveButton("Submit") { dialog, _ ->
                chronicDiseases = etChronicDiseases.text.toString() ?: ""
                previousOperations = etPreviousOperations.text.toString() ?: ""
                allergies = etAllergies.text.toString() ?: ""
                currentMedications = etCurrentMedications.text.toString() ?: ""
                comments = etComments.text.toString() ?: ""
                // Handle the collected data (e.g., send to API)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

}

