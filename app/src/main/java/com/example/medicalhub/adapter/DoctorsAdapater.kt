package com.training.medicalapp.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R
import com.model.GetAllDoctors

class DoctorsAdapater (
    private val doctors: List<GetAllDoctors>,
    private val clickListener: (GetAllDoctors) -> Unit
) : RecyclerView.Adapter<DoctorsAdapater.DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_drhomef_display_doctors_data, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(doctors[position], clickListener)
    }

    override fun getItemCount(): Int = doctors.size

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(doctor: GetAllDoctors, clickListener: (GetAllDoctors) -> Unit) {
            itemView.findViewById<TextView>(R.id.drNameee).text = doctor.userName
            itemView.findViewById<TextView>(R.id.drSpecializationnn).text = doctor.specialization


            itemView.setOnClickListener { clickListener(doctor) }

        }
    }
}