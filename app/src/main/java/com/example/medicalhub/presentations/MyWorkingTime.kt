package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.adapter.DayTimeAdapter
import com.example.medicalhub.adapter.DayTimeItem
import com.example.medicalhub.repository.Repository
import com.model.DaysOfWeek

class MyWorkingTime : AppCompatActivity() {

    private lateinit var dayTimeRecyclerView: RecyclerView
    private lateinit var plusIcon: ImageView
    private lateinit var adapter: DayTimeAdapter
    private lateinit var updateBtn: Button
    private val items = mutableListOf<DayTimeItem>()
    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_working_time)

        preference = SharedPrefManager(this)


        //        Creating a ViewModelFactory with the repository
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val docID = preference.getPrefVal(this).getString("docID", "")
        val token = preference.getPrefVal(this).getString("token", "")


        dayTimeRecyclerView = findViewById(R.id.dayTimeRecyclerView)
        plusIcon = findViewById(R.id.plusIcon)
        updateBtn = findViewById(R.id.updateBtn)

        adapter = DayTimeAdapter(this, items)
        dayTimeRecyclerView.layoutManager = LinearLayoutManager(this)
        dayTimeRecyclerView.adapter = adapter

        plusIcon.setOnClickListener {
            adapter.addItem()
        }
        // Load saved items
        adapter.loadItemsFromSharedPreferences()


        updateBtn.setOnClickListener {
            val daysOfWeek = mapItemsToDaysOfWeek()
            viewModel.PostDoctorWorkingDaysOfWeek("Bearer $token", daysOfWeek)
            viewModel.getRosheta.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    Log.d("abdo", "${response.body()}")

                }                })

        }
    }

    private fun mapItemsToDaysOfWeek(): DaysOfWeek {
        val docID = preference.getPrefVal(this).getString("docID", "")

        // Get the list of items from the adapter
        val items = adapter.getItems()
        Log.d("abdo", "items $items")

        // Initialize variables for each day
        var sunDayFrom = ""
        var sunDayTo = ""
        var monDayFrom = ""
        var monDayTo = ""
        var tuesDayFrom = ""
        var tuesDayTo = ""
        var wednesDayFrom = ""
        var wednesDayTo = ""
        var thursDayFrom = ""
        var thursDayTo = ""
        var friDayFrom = ""
        var friDayTo = ""
        var saturDayFrom = ""
        var saturDayTo = ""

        // Map items to the corresponding day variables
        for (item in items) {
            when (item.dayIndex) {
                0 -> { sunDayFrom = item.timeFrom; sunDayTo = item.timeTo }
                1 -> { monDayFrom = item.timeFrom; monDayTo = item.timeTo }
                2 -> { tuesDayFrom = item.timeFrom; tuesDayTo = item.timeTo }
                3 -> { wednesDayFrom = item.timeFrom; wednesDayTo = item.timeTo }
                4 -> { thursDayFrom = item.timeFrom; thursDayTo = item.timeTo }
                5 -> { friDayFrom = item.timeFrom; friDayTo = item.timeTo }
                6 -> { saturDayFrom = item.timeFrom; saturDayTo = item.timeTo }
            }
        }

        // Create and return the DaysOfWeek object
        return DaysOfWeek(
            doctorId = docID,
            sunDayFrom = sunDayFrom,
            sunDayTo = sunDayTo,
            monDayFrom = monDayFrom,
            monDayTo = monDayTo,
            tuesDayFrom = tuesDayFrom,
            tuesDayTo = tuesDayTo,
            wednesDayFrom = wednesDayFrom,
            wednesDayTo = wednesDayTo,
            thursDayFrom = thursDayFrom,
            thursDayTo = thursDayTo,
            friDayFrom = friDayFrom,
            friDayTo = friDayTo,
            saturDayFrom = saturDayFrom,
            saturDayTo = saturDayTo
        )
    }


}