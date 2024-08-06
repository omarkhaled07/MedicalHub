package com.example.medicalhub.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R

class patientAdapter : RecyclerView.Adapter<patientAdapter.MyViewHolder>() {
    private var myList = mutableListOf<String>()

    private var id = mutableListOf<String>()
    private var nationalID = mutableListOf<String>()
    private var email = mutableListOf<String>()
    private var phoneNumber = mutableListOf<String>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.itemList)
        val hiddenLayout: LinearLayout = itemView.findViewById(R.id.hiddenLayout)
        val hiddenText: TextView = itemView.findViewById(R.id.hiddenText)

        init {
            itemText.setOnClickListener {
                if (hiddenLayout.visibility == View.GONE) {
                    hiddenLayout.visibility = View.VISIBLE
                } else {
                    hiddenLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.get_all_patient,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemText.text = myList[position]
        val id = id[position]
        val nationalID = nationalID[position]
        val email = email[position]
        val phoneNumber = phoneNumber[position]
        val formattedText = """
            <b>processName:</b> $id<br/>
            <b>Severity:</b> $nationalID<br/>
            <b>description:</b> $email<br/>
            <b>time:</b> $phoneNumber
        """.trimIndent()

        holder.hiddenText.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_COMPACT)
    }


    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list1: List<String>, list2: List<String>, list3: List<String>, list4: List<String>, list5: List<String>) {
        myList.clear()
        myList.addAll(list1)
        id.clear()
        id.addAll(list2)
        nationalID.clear()
        nationalID.addAll(list3)
        email.clear()
        email.addAll(list4)
        phoneNumber.clear()
        phoneNumber.addAll(list5)
        notifyDataSetChanged()
    }
}