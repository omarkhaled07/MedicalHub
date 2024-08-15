package com.example.medicalhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R
import com.model.GetDayOfWeek


class GetDaysDoctorAdapter(private var days: List<GetDayOfWeek>,
                           private val onDayUpdated: (GetDayOfWeek) -> Unit
) : RecyclerView.Adapter<GetDaysDoctorAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_get_doctor_days_times, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position])
    }

    override fun getItemCount(): Int = days.size

    fun updateData(newDays: List<GetDayOfWeek>) {
        days = newDays
        notifyDataSetChanged()
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayName: TextView = itemView.findViewById(R.id.dayNamee)
        private val startTimeTextView: TextView = itemView.findViewById(R.id.startTimee)
        private val endTimeTextView: TextView = itemView.findViewById(R.id.endTimee)

        fun bind(day: GetDayOfWeek) {
            dayName.text = day.name
            startTimeTextView.text = day.startTime
            endTimeTextView.text = day.endTime

            itemView.setOnClickListener {
                showEditDialog(day, onDayUpdated)
            }

        }

        private fun showEditDialog(day: GetDayOfWeek, onDayUpdated: (GetDayOfWeek) -> Unit) {
            // Show a dialog where the doctor can edit the day, start time, and end time
            // Once the doctor saves the changes, invoke the onDayUpdated function with the new data
            val dialogView =
                LayoutInflater.from(itemView.context).inflate(R.layout.dialog_edit_doctor_day, null)
            val dialogBuilder = AlertDialog.Builder(itemView.context).setView(dialogView)
            val alertDialog = dialogBuilder.create()

            val dayNameTextView = dialogView.findViewById<TextView>(R.id.dayNameTextView)
            val startTimeEditText = dialogView.findViewById<EditText>(R.id.startTimeEditText)
            val endTimeEditText = dialogView.findViewById<EditText>(R.id.endTimeEditText)
            val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

            dayNameTextView.text = day.name
            startTimeEditText.setText(day.startTime)
            endTimeEditText.setText(day.endTime)

            saveButton.setOnClickListener {
                val updatedDay = day.copy(
                    startTime = startTimeEditText.text.toString(),
                    endTime = endTimeEditText.text.toString()
                )
                onDayUpdated(updatedDay)
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

    }
}


