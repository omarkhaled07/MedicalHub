package com.example.medicalhub.presentations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.adapter.PatientData
import com.example.medicalhub.adapter.PatientDataAdapter
import com.example.medicalhub.repository.Repository
import com.model.patientRosheta

class AllPatientHistory : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    private lateinit var patientDataAdapter: PatientDataAdapter
    private lateinit var patientDataRecyclerView: RecyclerView
    var body = listOf<patientRosheta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_patient_history)

        preference = SharedPrefManager(this)

//        Creating a ViewModelFactory with the repository
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val patID = preference.getPrefVal(this).getString("patID", "")
        val token = preference.getPrefVal(this).getString("tokenPatient", "")

        Log.d("abdo", "patID $patID")
        Log.d("abdo", "token $token")

        // Set up the patient data RecyclerView
        patientDataRecyclerView= findViewById(R.id.patientDataRecyclerView)

        patientDataRecyclerView.layoutManager = LinearLayoutManager(this)
        patientDataAdapter = PatientDataAdapter(listOf())
        patientDataRecyclerView.adapter = patientDataAdapter


        viewModel.getAllRoshetaByPatientID( "Bearer $token", patID!!)
        viewModel.getRosheta.observe(this, Observer { response ->
            if (response.isSuccessful) {

                body = response.body() ?: emptyList()
                Log.d("abdo", "body $body")
                // Update the adapter with the new data
                val patientDataList = body.map {
                    PatientData(
                        it.id,
                        it.diagnosis,
                        it.medicine,
                        it.analysis,
                        it.x_Rays,
                        it.additionalNotes,
                        it.doctorId,
                        it.patientId
                    )
                }
                patientDataAdapter = PatientDataAdapter(patientDataList)
                patientDataRecyclerView.adapter = patientDataAdapter


            }
            else{
                Log.e("abdo", "error")
            }
        })


    }
}