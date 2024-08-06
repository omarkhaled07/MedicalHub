package com.example.medicalhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R


data class PatientData(
    val id: String,
    val diagnosis: String,
    val medicine: String,
    val analysis: String,
    val x_Rays: String,
    val additionalNotes: String,
    val doctorId: String,
    val patientId: String
)

class PatientDataAdapter(private val patientDataList: List<PatientData>) :
    RecyclerView.Adapter<PatientDataAdapter.PatientDataViewHolder>() {

    class PatientDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diagnosis: TextView = itemView.findViewById(R.id.diagnosis)
        val medicine: TextView = itemView.findViewById(R.id.medicine)
        val analysis: TextView = itemView.findViewById(R.id.analysis)
        val xRays: TextView = itemView.findViewById(R.id.xRays)
        val additionalNotes: TextView = itemView.findViewById(R.id.additionalNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient_data, parent, false)
        return PatientDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientDataViewHolder, position: Int) {
        val patientData = patientDataList[position]
        holder.diagnosis.text = patientData.diagnosis
        holder.medicine.text = patientData.medicine
        holder.analysis.text = patientData.analysis
        holder.xRays.text = patientData.x_Rays
        holder.additionalNotes.text = patientData.additionalNotes
    }

    override fun getItemCount() = patientDataList.size
}