package com.example.medicalhub.presentations

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.repository.Repository
import com.model.patientRosheta

class PatientHistory : AppCompatActivity() {

//    private lateinit var viewModel: MainViewModel
//    private lateinit var preference: SharedPrefManager
//    var body = listOf<patientRosheta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_history)

//        preference = SharedPrefManager(this)

        // Creating a ViewModelFactory with the repository
//        val repository = Repository()
//        val viewModelFactory = MainViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
//        val patID = preference.getPrefVal(this).getString("patID", "")
//        val token = preference.getPrefVal(this).getString("tokenPatient", "")
//
//
//        viewModel.getAllRoshetaByPatientID(token!!, patID!!)
//        viewModel.getRosheta.observe(this, Observer { response ->
//            if (response.isSuccessful) {
//
//                body = response.body() ?: emptyList()
//                Log.d("abdo", "body $body")
//            }
//        })

    }
}