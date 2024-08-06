package com.example.medicalhub.presentations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.medicalhub.R

class PatNewReservation : AppCompatActivity() {
    lateinit var backArrow : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pat_new_reservation)

        backArrow = findViewById(R.id.backArrow)

        backArrow.setOnClickListener {
            onBackPressed()
        }

    }
}