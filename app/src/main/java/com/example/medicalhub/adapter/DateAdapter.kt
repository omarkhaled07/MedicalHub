package com.example.medicalhub.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R


class DateAdapter(
    private val dates: List<String>,
    private val onDateSelected: (String) -> Unit
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectedPosition = -1

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateButton: Button = itemView.findViewById(R.id.dateButton)

        @SuppressLint("ResourceAsColor")
        fun bind(date: String, position: Int) {
            dateButton.text = date
            dateButton.setOnClickListener {
                selectedPosition = position
                onDateSelected(date)
                notifyDataSetChanged()
            }
            val selectedColor = ContextCompat.getColor(itemView.context, R.color.blue)

            dateButton.setBackgroundColor(
                if (selectedPosition == position) selectedColor else Color.WHITE
            )
            dateButton.setTextColor(
                if (selectedPosition == position)  Color.WHITE else Color.BLACK
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_button, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dates[position], position)
    }

    override fun getItemCount() = dates.size
}