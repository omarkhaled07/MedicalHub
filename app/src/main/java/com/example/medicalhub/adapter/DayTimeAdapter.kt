package com.example.medicalhub.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.R

class DayTimeAdapter(private val context: Context, private val items: MutableList<DayTimeItem>) :
    RecyclerView.Adapter<DayTimeAdapter.ViewHolder>() {

    private val daysOfWeek = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    private val sharedPrefManager = SharedPrefManager(context)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val daySpinner: Spinner = itemView.findViewById(R.id.daySpinner)
        val timeFrom: EditText = itemView.findViewById(R.id.timeFrom)
        val timeTo: EditText = itemView.findViewById(R.id.timeTo)
        val deleteItemButton: ImageView = itemView.findViewById(R.id.deleteItemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day_time, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Populate Spinner programmatically with custom layout
        val adapter = ArrayAdapter(
            holder.itemView.context,
            R.layout.custom_spinner_item, // Custom layout for spinner item
            daysOfWeek
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        holder.daySpinner.adapter = adapter
        holder.daySpinner.setSelection(item.dayIndex)

        holder.timeFrom.setText(item.timeFrom)
        holder.timeTo.setText(item.timeTo)

        // Handle item deletion
        holder.deleteItemButton.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Method to add a new item
    fun addItem() {
        items.add(DayTimeItem())
        notifyItemInserted(items.size - 1)
        saveItemsToSharedPreferences()
    }

    // Method to save items to SharedPreferences
    private fun saveItemsToSharedPreferences() {
        sharedPrefManager.saveDayTimeItemList("dayTimeItems", items)
    }

    // Method to load items from SharedPreferences
    fun loadItemsFromSharedPreferences() {
        val loadedItems = sharedPrefManager.getDayTimeItemList("dayTimeItems")
        if (loadedItems.isNotEmpty()) {
            items.clear()
            items.addAll(loadedItems)
            notifyDataSetChanged()
        }
    }
    // Method to get the current list of items
    fun getItems(): List<DayTimeItem> {
        return items
    }

}

// Data class for the item
data class DayTimeItem(
    var dayIndex: Int = 0,
    var timeFrom: String = "",
    var timeTo: String = ""
)