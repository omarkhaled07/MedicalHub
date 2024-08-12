package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.adapter.DoctorRoshetaHistoryAdapter
import com.example.medicalhub.adapter.PatientData
import com.example.medicalhub.adapter.PatientDataAdapter
import com.example.medicalhub.repository.Repository
import com.model.allDrRosheta
import com.model.patientRosheta

class DoctorHistoryRosheta : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    private lateinit var docDataAdapter: DoctorRoshetaHistoryAdapter
    private lateinit var docDataRecyclerView: RecyclerView
    var body = listOf<allDrRosheta>()
//Hello
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
        docDataAdapter = DoctorRoshetaHistoryAdapter()
        docDataRecyclerView.adapter = docDataAdapter

        viewModel.getAllRoshetaByDrID( "Bearer $token", docID!!)
        viewModel.getalldrRosheta.observe(this, Observer { response ->
            if (response.isSuccessful) {

                body = response.body() ?: emptyList()
                Log.d("abdo", "body $body")
                // Update the adapter with the new data
                val userID = body.map { it.id }
                val date = body.map { it.createdOn.substringBefore("T") }
                val diagnosis = body.map { it.diagnosis }
                val medicine = body.map { it.medicine }
                val analysis = body.map { it.analysis }
                val x_Rays = body.map { it.x_Rays }
                val additionalNotes = body.map { it.additionalNotes }
                val patientCode = body.map { it.patientCode.toString() }

                docDataAdapter.setData(patientCode,date, diagnosis, medicine, analysis, x_Rays, additionalNotes)



            }
            else{
                Log.e("abdo", "error")
            }
        })

    // Search filter
    val searchEditText = findViewById<EditText>(R.id.search)
    searchEditText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            docDataAdapter.filter(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })

    }
}