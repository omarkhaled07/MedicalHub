package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TimePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.adapter.WorkingTimeAdapter
import com.example.medicalhub.repository.Repository

import com.model.DayTimeItemDoctor
import com.model.StructuredWorkingTimes

class MyWorkingTime : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPrefManager
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerDays: Spinner
    private lateinit var timePickerFrom: TimePicker
    private lateinit var timePickerTo: TimePicker
    private lateinit var buttonadd: Button
    private lateinit var updateWorkingTime: Button
    private lateinit var setWorkingTime: Button
    private lateinit var adapter: WorkingTimeAdapter
    private val workingTimes = mutableListOf<DayTimeItemDoctor>()
    private lateinit var availableDays: MutableList<String>
    private var docID: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_working_time)

        sharedPreferences = SharedPrefManager(this)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        var savedValue = sharedPreferences.getPrefVal(this).getString("set", "")
        val token = sharedPreferences.getPrefVal(this).getString("token", "")
        docID = sharedPreferences.getPrefVal(this).getString("docID", "")!!
        availableDays = resources.getStringArray(R.array.days_of_week).toMutableList()

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, availableDays)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDays = findViewById(R.id.spinner_days)
        spinnerDays.adapter = adapterSpinner
        Log.d("abdo", "savedValue before $savedValue")


        spinnerDays = findViewById(R.id.spinner_days)
        timePickerFrom = findViewById(R.id.timePicker_from)
        timePickerTo = findViewById(R.id.timePicker_to)
        buttonadd = findViewById(R.id.button_save)
        recyclerView = findViewById(R.id.recyclerView_working_times)
        setWorkingTime = findViewById(R.id.setWorkingTime)
        updateWorkingTime = findViewById(R.id.updateWorkingTime)
        // Set 24-hour view
        timePickerFrom.setIs24HourView(true)
        timePickerTo.setIs24HourView(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WorkingTimeAdapter(workingTimes) { position ->
            deleteWorkingTime(position)
        }
        recyclerView.adapter = adapter


        // Load saved working times
        loadWorkingTimes()

        // Update button visibility based on the saved "set" value
        if (savedValue != docID) {
            setWorkingTime.visibility = View.VISIBLE
            updateWorkingTime.visibility = View.GONE
        } else {
            setWorkingTime.visibility = View.GONE
            updateWorkingTime.visibility = View.VISIBLE
        }

        setWorkingTime.setOnClickListener {
            val structuredTimes = mapWorkingTimesToStructuredFormat(workingTimes)
            viewModel.PostDoctorWorkingDaysOfWeek("Bearer $token", structuredTimes)
            viewModel.postDocWorkingTime.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    val response = response.body()
                    Log.d("Abdo", "response $response")
                    sharedPreferences.setPrefVal(this, "set", docID).toString()
                    setWorkingTime.visibility = View.GONE
                    updateWorkingTime.visibility = View.VISIBLE
                } else {
                    Log.e("Abdo", "erroe $response")
                    Log.e("Abdo", "structuredTimes $structuredTimes")
                }
            })
        }

        updateWorkingTime.setOnClickListener {
            val structuredTimes = mapWorkingTimesToStructuredFormat(workingTimes)
            viewModel.EditDoctorWorkingDaysOfWeek("Bearer $token", structuredTimes)
            viewModel.editDocWorkingTime.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    val response = response.body()
                    Log.d("Abdo", "response edit $response")

                } else {
                    Log.e("Abdo", "erroe $response")
                    Log.e("Abdo", "structuredTimes $structuredTimes")
                }
            })
        }


        buttonadd.setOnClickListener {
            val selectedDay = spinnerDays.selectedItem.toString()
            val dayTime = DayTimeItemDoctor(
                day = spinnerDays.selectedItem.toString(),
                fromHour = timePickerFrom.hour,
                fromMinute = timePickerFrom.minute,
                toHour = timePickerTo.hour,
                toMinute = timePickerTo.minute,
            )
            Log.d("abdo", "dayTime $dayTime")
            workingTimes.add(dayTime)
            adapter.notifyDataSetChanged()
            saveWorkingTimes()

            // Remove the selected day from availableDays and update the spinner
            availableDays.remove(selectedDay)
            adapterSpinner.notifyDataSetChanged()
        }


    }

    private fun deleteWorkingTime(position: Int) {
        val removedDay = workingTimes[position].day
        workingTimes.removeAt(position)
        adapter.notifyItemRemoved(position)
        saveWorkingTimes()

        // Add the removed day back to availableDays and update the spinner
        availableDays.add(removedDay)
        availableDays.sort()
        (spinnerDays.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }

    private fun saveWorkingTimes() {
        sharedPreferences.saveWorkingTimeList("working_times", workingTimes)
    }

    private fun loadWorkingTimes() {
        val savedWorkingTimes = sharedPreferences.getWorkingTimeList("working_times")
        workingTimes.clear()
        workingTimes.addAll(savedWorkingTimes)

        // Update availableDays to remove already selected days
        for (workingTime in workingTimes) {
            availableDays.remove(workingTime.day)
        }

        adapter.notifyDataSetChanged()
        (spinnerDays.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }

    private fun mapWorkingTimesToStructuredFormat(workingTimes: List<DayTimeItemDoctor>): StructuredWorkingTimes {
        val structuredTimes = StructuredWorkingTimes()
        for (workingTime in workingTimes) {
            val fromTime = String.format("%02d:%02d", workingTime.fromHour, workingTime.fromMinute)
            val toTime = String.format("%02d:%02d", workingTime.toHour, workingTime.toMinute)

            when (workingTime.day) {
                "Sunday" -> {
                    structuredTimes.sunDayFrom = fromTime
                    structuredTimes.sunDayTo = toTime
                }

                "Monday" -> {
                    structuredTimes.monDayFrom = fromTime
                    structuredTimes.monDayTo = toTime
                }

                "Tuesday" -> {
                    structuredTimes.tuesDayFrom = fromTime
                    structuredTimes.tuesDayTo = toTime
                }

                "Wednesday" -> {
                    structuredTimes.wednesDayFrom = fromTime
                    structuredTimes.wednesDayTo = toTime
                }

                "Thursday" -> {
                    structuredTimes.thursDayFrom = fromTime
                    structuredTimes.thursDayTo = toTime
                }

                "Friday" -> {
                    structuredTimes.friDayFrom = fromTime
                    structuredTimes.friDayTo = toTime
                }

                "Saturday" -> {
                    structuredTimes.saturDayFrom = fromTime
                    structuredTimes.saturDayTo = toTime
                }
            }
            structuredTimes.doctorId = docID
        }

        return structuredTimes
    }


}