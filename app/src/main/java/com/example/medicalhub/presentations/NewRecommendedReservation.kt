package com.example.medicalhub.presentations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.adapter.DateAdapter
import com.example.medicalhub.adapter.TimeAdapter
import com.example.medicalhub.repository.Repository
import com.model.AllPateintData
import com.model.BookDate
import com.model.ResultInterval

class NewRecommendedReservation : AppCompatActivity() {

    private lateinit var recyclerViewDates: RecyclerView
    private lateinit var recyclerViewTimes: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var drName: TextView
    private lateinit var drSpecialization: TextView
    private lateinit var backArrow: ImageView
    private var selectedDate: String? = null
    private var selectedTime: ResultInterval? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    private var allDrTime = listOf<ResultInterval>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recommended_reservation)

        preference = SharedPrefManager(this)
        recyclerViewDates = findViewById(R.id.recyclerViewDates)
        recyclerViewTimes = findViewById(R.id.recyclerViewTimes)
        drName = findViewById(R.id.drName)
        btnAdd = findViewById(R.id.btnAdd)
        backArrow = findViewById(R.id.backArrow)
        drSpecialization = findViewById(R.id.drSpecialization)

        val docID = preference.getPrefVal(this).getString("saveDrID", "")
        val token = preference.getPrefVal(this).getString("tokenPatient", "")
        val saveDrName = preference.getPrefVal(this).getString("saveDrName", "")
        val saveDrSpecialization = preference.getPrefVal(this).getString("saveDrSpecialization", "")

        drName.text = saveDrName
        drSpecialization.text = saveDrSpecialization


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]



        viewModel.getAllStoredWorkingTimes("Bearer $token", docID!!)
        viewModel.getIntervalTimes.observe(this, Observer { response ->
            if (response.isSuccessful) {
                allDrTime = response.body()?.result ?: emptyList()
                setupDateRecyclerView()
            }
        })



        backArrow.setOnClickListener {
            onBackPressed()
        }

        btnAdd.setOnClickListener {
            val bookDate = BookDate(
                patientId = preference.getPrefVal(this).getString("patientID", "") ?: "",
                doctorTimeIntervalId = selectedTime?.id ?: 0,
            )

            viewModel.bookDate("Bearer $token", bookDate)
            viewModel.bookPatient.observe(this, Observer { response ->
                if (response.isSuccessful) {
                              Toast.makeText(
                this,
                "You booked The Date Successfully: $selectedDate at ${selectedTime?.intervalStart}",
                Toast.LENGTH_LONG
            ).show()
                }
            })
        }
    }

    private fun setupDateRecyclerView() {
        val groupedData = allDrTime.groupBy { it.intervalStart.split("T")[0] }
        val dateAdapter = DateAdapter(groupedData.keys.toList()) { date ->
            selectedDate = date
            setupTimeRecyclerView(groupedData[date] ?: emptyList())
            btnAdd.isEnabled = false
        }
        recyclerViewDates.layoutManager = GridLayoutManager(this, 3)
        recyclerViewDates.adapter = dateAdapter
    }

    private fun setupTimeRecyclerView(times: List<ResultInterval>) {
        val timeAdapter = TimeAdapter(times) { time ->
            selectedTime = time
            btnAdd.isEnabled = true
        }
        recyclerViewTimes.layoutManager = LinearLayoutManager(this)
        recyclerViewTimes.adapter = timeAdapter
    }
}