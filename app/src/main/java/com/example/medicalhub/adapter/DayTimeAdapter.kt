package com.example.medicalhub.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.R
import com.model.DayTimeItemDoctor

class WorkingTimeAdapter(
    private val workingTimes: MutableList<DayTimeItemDoctor>,
    private val onDelete: (Int) -> Unit // Callback for handling deletions
) : RecyclerView.Adapter<WorkingTimeAdapter.WorkingTimeViewHolder>() {

    inner class WorkingTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTextView: TextView = itemView.findViewById(R.id.textView_day)
        val fromTimeTextView: TextView = itemView.findViewById(R.id.textView_from_time)
        val toTimeTextView: TextView = itemView.findViewById(R.id.textView_to_time)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteItemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkingTimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_working_time, parent, false)
        return WorkingTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkingTimeViewHolder, position: Int) {
        val workingTime = workingTimes[position]
        holder.dayTextView.text = workingTime.day
        holder.fromTimeTextView.text = String.format("%02d:%02d", workingTime.fromHour, workingTime.fromMinute)
        holder.toTimeTextView.text = String.format("%02d:%02d", workingTime.toHour, workingTime.toMinute)

        // Handle item deletion
        holder.deleteButton.setOnClickListener {
            onDelete(position) // Call the deletion callback
        }
    }

    override fun getItemCount(): Int = workingTimes.size
}