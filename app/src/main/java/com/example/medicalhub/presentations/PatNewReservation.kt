package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.repository.Repository
import com.model.GetAllDoctors
import com.training.medicalapp.ui.Adapters.DoctorsAdapater

class PatNewReservation : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager

    lateinit var backArrow: ImageView
    lateinit var RecommendedReservation: CardView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pat_new_reservation)

        preference = SharedPrefManager(this)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]


        backArrow = findViewById(R.id.backArrow)
        RecommendedReservation = findViewById(R.id.RecommendedReservation)

        backArrow.setOnClickListener {
            onBackPressed()
        }

        RecommendedReservation.setOnClickListener {
            startActivity(Intent(this, NewRecommendedReservation::class.java))
        }

        // Initialize RecyclerView
        val doctorRecyclerView = findViewById<RecyclerView>(R.id.doctorRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        doctorRecyclerView.layoutManager = layoutManager


        // Create empty adapter
        val doctors = mutableListOf<GetAllDoctors>()
        val doctorAdapter = DoctorsAdapater(doctors) { doctor ->
            preference.setPrefVal(this, "saveDrName", doctor.userName)
            preference.setPrefVal(this, "saveDrSpecialization", doctor.specialization.toString())
            preference.setPrefVal(this, "saveDrID", doctor.id)
            // Handle item click here, e.g., navigate to the details fragment
            startActivity(Intent(this, NewRecommendedReservation::class.java))
        }

        // Set adapter to RecyclerView
        doctorRecyclerView.adapter = doctorAdapter

        val token = preference.getPrefVal(this).getString("tokenPatient", "")

        // Make API call
        viewModel.getAllDoctors("Bearer $token")
        viewModel.getallactiveDr.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val doctorList = response.body()

                doctorList?.forEach { doctor ->
                    Log.d("DoctorSpecialization", doctor.specialization ?: "No Specialization")
                }
                // Update adapter data
                doctorList?.let {
                    doctors.clear()
                    doctors.addAll(it)
                    doctorAdapter.notifyDataSetChanged()
                }

            } else {
                Log.d("unResponse", response.code().toString())
            }
        })
    }

}
