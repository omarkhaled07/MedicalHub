package com.example.medicalhub.adapter

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R
import com.model.DayOfWeek
import java.util.Calendar

class DaysDoctorAdapter(private val days: List<DayOfWeek>, private val onDaySelected: (DayOfWeek) -> Unit) :
    RecyclerView.Adapter<DaysDoctorAdapter.DayViewHolder>() {

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayName: TextView = itemView.findViewById(R.id.dayName)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxDays)
        private val timeLayout: LinearLayout = itemView.findViewById(R.id.timeLayout)
        private val startTimeButton: Button = itemView.findViewById(R.id.startTimeButton)
        private val endTimeButton: Button = itemView.findViewById(R.id.endTimeButton)

        fun bind(day: DayOfWeek) {
            dayName.text = day.name
            checkBox.isChecked = day.isSelected
            timeLayout.visibility = if (day.isSelected) View.VISIBLE else View.GONE

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                day.isSelected = isChecked
                timeLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
                onDaySelected(day)
            }

            startTimeButton.text = day.startTime
            endTimeButton.text = day.endTime

            startTimeButton.setOnClickListener {
                showTimePicker(day, true)
            }

            endTimeButton.setOnClickListener {
                showTimePicker(day, false)
            }
        }

        private fun showTimePicker(day: DayOfWeek, isStartTime: Boolean) {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(itemView.context, { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                if (isStartTime) {
                    day.startTime = time
                    startTimeButton.text = time
                } else {
                    day.endTime = time
                    endTimeButton.text = time
                }
                onDaySelected(day)
            }, hour, minute, true)

            timePickerDialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position])
    }

    override fun getItemCount(): Int = days.size
}
