package com.example.medicalhub.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R
import com.model.ResultInterval


class TimeAdapter(
    private val times: List<ResultInterval>,
    private val onTimeSelected: (ResultInterval) -> Unit
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private var selectedPosition = -1

    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeButton: Button = itemView.findViewById(R.id.timeButton)

        @SuppressLint("ResourceAsColor")
        fun bind(time: ResultInterval, position: Int) {
            timeButton.text = "${time.intervalStart.split("T")[1]} - ${time.intervalEnd.split("T")[1]}"
            timeButton.setOnClickListener {
                selectedPosition = position
                onTimeSelected(time)
                notifyDataSetChanged()
            }
            val selectedColor = ContextCompat.getColor(itemView.context, R.color.blue)
            timeButton.setBackgroundColor(
                if (selectedPosition == position) selectedColor else Color.WHITE
            )

            timeButton.setTextColor(
                if (selectedPosition == position)  Color.WHITE else Color.BLACK
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time_button, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(times[position], position)
    }

    override fun getItemCount() = times.size
}