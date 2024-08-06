package com.example.medicalhub.presentations

import android.annotation.SuppressLint
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
import com.model.allDrRosheta
import com.model.patientRosheta

class DoctorHistoryRosheta : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    private lateinit var docDataAdapter: PatientDataAdapter
    private lateinit var docDataRecyclerView: RecyclerView
    var body = listOf<allDrRosheta>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_history)

        preference = SharedPrefManager(this)

        //        Creating a ViewModelFactory with the repository
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val docID = preference.getPrefVal(this).getString("docID", "")
        val token = preference.getPrefVal(this).getString("token", "")


        // Set up the patient data RecyclerView
        docDataRecyclerView= findViewById(R.id.docDataRecyclerView)

        docDataRecyclerView.layoutManager = LinearLayoutManager(this)
        docDataAdapter = PatientDataAdapter(listOf())
        docDataRecyclerView.adapter = docDataAdapter

        viewModel.getAllRoshetaByDrID( "Bearer $token", docID!!)
        viewModel.getalldrRosheta.observe(this, Observer { response ->
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
                docDataAdapter = PatientDataAdapter(patientDataList)
                docDataRecyclerView.adapter = docDataAdapter


            }
            else{
                Log.e("abdo", "error")
            }
        })

    }
}