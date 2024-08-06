package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.adapter.patientAdapter
import com.example.medicalhub.repository.Repository

class AllPatientsReservation : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: patientAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var preference: SharedPrefManager
    private lateinit var id: ArrayList<String>
    private lateinit var nationalID: ArrayList<String>
    private lateinit var email: ArrayList<String>
    private lateinit var phoneNumber: ArrayList<String>
    private lateinit var userName: ArrayList<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_patients_reservation)

        preference = SharedPrefManager(this)
        val token = preference.getPrefVal(this).getString("token", "")

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = patientAdapter()
        recyclerView.adapter = adapter

        viewModel.getAllPatient("Bearer $token")
        viewModel.getAllPatients.observe(this, Observer { response ->
            if (response.isSuccessful) {
//                id = (response.body()?.id

//                nationalID = (response.body()?.data?.map { it.nationalID }
//                    ?: emptyList()) as ArrayList<String>

//                userName = (response.body()?.data?.map { it.userName }
//                    ?: emptyList()) as ArrayList<String>
//
//                email = (response.body()?.data?.map { it.email }
//                    ?: emptyList()) as ArrayList<String>
//
//                phoneNumber = (response.body()?.data?.map { it.phoneNumber }
//                    ?: emptyList()) as ArrayList<String>

//                adapter?.setData(userName, id, nationalID, email, phoneNumber)

            }            })
    }
}