package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TimePicker
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
import com.example.medicalhub.adapter.DaysDoctorAdapter
import com.example.medicalhub.adapter.GetDaysDoctorAdapter
import com.example.medicalhub.adapter.WorkingTimeAdapter
import com.example.medicalhub.repository.Repository
import com.model.DayOfWeek

import com.model.DayTimeItemDoctor
import com.model.GetDayOfWeek
import com.model.PUTDoctorWorkingDaysOfWeek
import com.model.StructuredWorkingTimes

class MyWorkingTime : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPrefManager
    private lateinit var viewModel: MainViewModel
    private lateinit var submitButton: Button
    private lateinit var backArrow: ImageView
    private lateinit var days: List<DayOfWeek>
    private var docID: String = ""
    private var token: String = ""
    private var doctorId = 0
    private var daysList: MutableList<GetDayOfWeek> = mutableListOf()
    var buttonVisibilty: Boolean = true
    private lateinit var daysAdapter: GetDaysDoctorAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_working_time)

        submitButton = findViewById(R.id.submitButton)
        sharedPreferences = SharedPrefManager(this)
        docID = sharedPreferences.getPrefVal(this).getString("docID", "")!!
        buttonVisibilty = sharedPreferences.getBoolean("isVisible $docID")
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        Log.d("abdo", "buttonVisibilty $buttonVisibilty")
        backArrow = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            onBackPressed()
        }
        if (buttonVisibilty) {
            submitButton.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
        } else {
            submitButton.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
        var savedValue = sharedPreferences.getPrefVal(this).getString("set", "")
        token = sharedPreferences.getPrefVal(this).getString("token", "")!!



        days = listOf(
            DayOfWeek("Sunday"),
            DayOfWeek("Monday"),
            DayOfWeek("Tuesday"),
            DayOfWeek("Wednesday"),
            DayOfWeek("Thursday"),
            DayOfWeek("Friday"),
            DayOfWeek("Saturday")

        )


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = DaysDoctorAdapter(days) { day ->
            // Handle day selection
            // Handle day selection

            Toast.makeText(
                this,
                "${day.name} selected: ${day.isSelected}, Start: ${day.startTime}, End: ${day.endTime}",
                Toast.LENGTH_SHORT
            ).show()
        }
        // Initialize RecyclerView
        val getDoctorRecyclerView = findViewById<RecyclerView>(R.id.displayDrRv)
        val layoutManager2 = GridLayoutManager(this, 3)
        layoutManager2.orientation = RecyclerView.HORIZONTAL
        getDoctorRecyclerView.layoutManager = layoutManager2

        daysAdapter = GetDaysDoctorAdapter(daysList) { updatedDay ->
            updateDoctorDay(updatedDay)
        }
        getDoctorRecyclerView.adapter = daysAdapter

        // Fetch data from ViewModel
        fetchDoctorAvailability()

        submitButton.setOnClickListener {
            try {
                val doctorAvailability = StructuredWorkingTimes(
                    sunDayFrom = days[0].startTime,
                    sunDayTo = days[0].endTime,
                    monDayFrom = days[1].startTime,
                    monDayTo = days[1].endTime,
                    tuesDayFrom = days[2].startTime,
                    tuesDayTo = days[2].endTime,
                    wednesDayFrom = days[3].startTime,
                    wednesDayTo = days[3].endTime,
                    thursDayFrom = days[4].startTime,
                    thursDayTo = days[4].endTime,
                    friDayFrom = days[5].startTime,
                    friDayTo = days[5].endTime,
                    saturDayFrom = days[6].startTime,
                    saturDayTo = days[6].endTime,
                    doctorId = docID
                )
                Log.d("responsee", doctorAvailability.toString())
                // Check if doctorAvailability object is properly populated
                if (doctorAvailability != null) {
                    viewModel.PostDoctorWorkingDaysOfWeek("Bearer $token", doctorAvailability)
                    viewModel.postDocWorkingTime.observe(this, Observer { response ->
                        if (response.isSuccessful) {
                            recyclerView.visibility = View.GONE
                            days.forEach { day ->
                                Log.d(
                                    "responsee",
                                    "${day.name}: Start - ${day.startTime}, End - ${day.endTime}"
                                )
                                fetchDoctorAvailability()
                            }
                            response.body()?.let {
                                Log.d("responsee", response.code().toString())
                                Log.d("responsee", doctorAvailability.toString())
                                Toast.makeText(this, "Response is successful", Toast.LENGTH_SHORT)
                                    .show()
                            } ?: run {
                                Log.d("responsee", "Response body is null")
                            }
                            submitButton.visibility = View.GONE
                            sharedPreferences.saveBoolean("isVisible $docID", false)
                        } else {
                            Log.d("responsee", "Error code: ${response.code()}")
                        }
                    })
                } else {
                    Log.d("responsee", "DoctorAvailability object is null")
                }
            } catch (e: Exception) {
                Log.e("responsee", "Exception caught: ${e.message}", e)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        // Fetch updated data and update RecyclerView
        fetchDoctorAvailability()
    }

    private fun updateDoctorDay(updatedDay: GetDayOfWeek) {
        // Find the corresponding day in the daysList and update it
        val index = daysList.indexOfFirst { it.name == updatedDay.name }
        if (index != -1) {
            daysList[index] = updatedDay
            daysAdapter.updateData(daysList)  // Notify the adapter that the data has changed

            // Construct the UpdateDoctorAvailability object with updated data from daysList
            val updateDoctorAvailability = PUTDoctorWorkingDaysOfWeek(
                id = doctorId,
                sunDayFrom = daysList.find { it.name == "Sunday" }?.startTime.orEmpty(),
                sunDayTo = daysList.find { it.name == "Sunday" }?.endTime.orEmpty(),
                monDayFrom = daysList.find { it.name == "Monday" }?.startTime.orEmpty(),
                monDayTo = daysList.find { it.name == "Monday" }?.endTime.orEmpty(),
                tuesDayFrom = daysList.find { it.name == "Tuesday" }?.startTime.orEmpty(),
                tuesDayTo = daysList.find { it.name == "Tuesday" }?.endTime.orEmpty(),
                wednesDayFrom = daysList.find { it.name == "Wednesday" }?.startTime.orEmpty(),
                wednesDayTo = daysList.find { it.name == "Wednesday" }?.endTime.orEmpty(),
                thursDayFrom = daysList.find { it.name == "Thursday" }?.startTime.orEmpty(),
                thursDayTo = daysList.find { it.name == "Thursday" }?.endTime.orEmpty(),
                friDayFrom = daysList.find { it.name == "Friday" }?.startTime.orEmpty(),
                friDayTo = daysList.find { it.name == "Friday" }?.endTime.orEmpty(),
                saturDayFrom = daysList.find { it.name == "Saturday" }?.startTime.orEmpty(),
                saturDayTo = daysList.find { it.name == "Saturday" }?.endTime.orEmpty(),
                doctorId = docID,
                doctor = null
            )


// Call the updateDoctorAvailability function to send the updated data to the server
            viewModel.EditDoctorWorkingDaysOfWeek("Bearer $token", updateDoctorAvailability)
            viewModel.editDocWorkingTime.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    Log.d("responsee", "Update successful: ${response.code()}")
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("responsee", "Update failed with error code: ${response.code()}")
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Log.e("responsee", "Day not found in daysList")
        }
    }

    private fun fetchDoctorAvailability() {
        viewModel.getAllDaysOfWeekByDrID("Bearer $token", docID)
        viewModel.getAllDaysbiID.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let { availabilityList ->
                    if (availabilityList.isNotEmpty()) {
                        val availability = availabilityList.first()
                        doctorId = availability.id
                        daysList = listOf(
                            GetDayOfWeek("Sunday", availability.sunDayFrom, availability.sunDayTo),
                            GetDayOfWeek("Monday", availability.monDayFrom, availability.monDayTo),
                            GetDayOfWeek(
                                "Tuesday",
                                availability.tuesDayFrom,
                                availability.tuesDayTo
                            ),
                            GetDayOfWeek(
                                "Wednesday",
                                availability.wednesDayFrom,
                                availability.wednesDayTo
                            ),
                            GetDayOfWeek(
                                "Thursday",
                                availability.thursDayFrom,
                                availability.thursDayTo
                            ),
                            GetDayOfWeek("Friday", availability.friDayFrom, availability.friDayTo),
                            GetDayOfWeek(
                                "Saturday",
                                availability.saturDayFrom,
                                availability.saturDayTo
                            )
                        ).toMutableList()
                        daysAdapter.updateData(daysList)
                    } else {
                        Log.e("responsee", "Availability list is empty")
                    }
                } ?: run {
                    Log.e("responsee", "Response body is null")
                }
            } else {
                Log.e("responsee", "Error code: ${response.code()}")
            }
        })
    }
}