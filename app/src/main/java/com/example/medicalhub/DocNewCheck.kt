package com.example.medicalhub

import CustomAdapter
import SelectedItemsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.HomeActivity
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.repository.Repository
import com.model.AllPateintData
import com.model.MedicinDescription

class DocNewCheck : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    lateinit var nationalId: EditText
    lateinit var userName: EditText
    lateinit var phoneNumber: EditText
    lateinit var mail: EditText
    lateinit var address: EditText
    private lateinit var adapter2: SelectedItemsAdapter
    private lateinit var adapter3: CustomAdapter
    private lateinit var adapter4: SelectedItemsAdapter
    private lateinit var adapter5: SelectedItemsAdapter

    lateinit var diagnosisAlertBtn: Button
    lateinit var DiagnosisRecyclerView: RecyclerView

    private val selectedDiagnosisList = mutableListOf<String>()
    private val selectedAnalysisList = mutableListOf<String>()
    private val selectedMedicinList = mutableListOf<String>()
    private val selectedXRayList = mutableListOf<String>()

    lateinit var MedicinRecyclerView: RecyclerView
    lateinit var MedicinAlertBtn: Button

    lateinit var AnalysisAlertButton: Button
    lateinit var AnalysisRecyclerView: RecyclerView

    lateinit var XRayAlertButton: Button
    lateinit var XRayRecyclerView: RecyclerView

    lateinit var submit: Button


    var body = listOf<AllPateintData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_new_check)


        preference = SharedPrefManager(this)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        nationalId = findViewById(R.id.NationalId)
        userName = findViewById(R.id.UserName)
        phoneNumber = findViewById(R.id.PhoneNumber)
        mail = findViewById(R.id.Age)
        address = findViewById(R.id.insuranceID)

        diagnosisAlertBtn = findViewById(R.id.diagnosisAlertBtn)
        DiagnosisRecyclerView = findViewById(R.id.DiagnosisRecyclerView)

        MedicinRecyclerView = findViewById(R.id.MedicinRecyclerView)

        MedicinAlertBtn = findViewById(R.id.MedicinAlertBtn)

        AnalysisAlertButton = findViewById(R.id.AnalysisAlertButton)
        AnalysisRecyclerView = findViewById(R.id.AnalysisRecyclerView)

        XRayAlertButton = findViewById(R.id.XRayAlertButton)
        XRayRecyclerView = findViewById(R.id.XRayRecyclerView)

        submit = findViewById(R.id.submit)



        val token = preference.getPrefVal(this).getString("token", "")


        viewModel.getAllPatient("Bearer $token")
        viewModel.getAllPatients.observe(this, Observer { response ->
            if (response.isSuccessful) {
                body = response.body() ?: emptyList()
                Log.d("newCheck", "body $body")

            }
        })

        val AnalysisLayoutManager = GridLayoutManager(this, 3)
        AnalysisRecyclerView.layoutManager = AnalysisLayoutManager
        adapter2 = SelectedItemsAdapter(selectedAnalysisList)
        AnalysisRecyclerView.adapter = adapter2

        val MedicinLayoutManager = GridLayoutManager(this, 1)
        MedicinRecyclerView.layoutManager = MedicinLayoutManager
        adapter3 = CustomAdapter(selectedMedicinList)
        MedicinRecyclerView.adapter = adapter3

        val DiagnosisLayoutManager = GridLayoutManager(this, 2)
        DiagnosisRecyclerView.layoutManager = DiagnosisLayoutManager
        adapter4 = SelectedItemsAdapter(selectedDiagnosisList)
        DiagnosisRecyclerView.adapter = adapter4

        val XRayLayoutManager = GridLayoutManager(this, 3)
        XRayRecyclerView.layoutManager = XRayLayoutManager
        adapter5 = SelectedItemsAdapter(selectedXRayList)
        XRayRecyclerView.adapter = adapter5

        AddDiagnosis()
        AddMedicin()
        AddAnalysis()
        AddXRays()

        submit.setOnClickListener {
            val selectedDiagnosis = adapter4.getSelectedItems()
            val selectedMedicins = adapter3.getSelectedItems()
            val selectedAnalyses = adapter2.getSelectedItems()
            val selectedXRays = adapter5.getSelectedItems()

            Log.d("abdo", "selectedDiagnosis $selectedDiagnosis")
            Log.d("abdo", "selectedMedicins $selectedMedicins")
            Log.d("abdo", "selectedAnalyses $selectedAnalyses")
            Log.d("abdo", "selectedXRays $selectedXRays")

            var medicinDesription = MedicinDescription(
                diagnosis = selectedDiagnosis.joinToString(", "),
                medicine = selectedMedicins.joinToString(", "),
                analysis = selectedAnalyses.joinToString(", "),
                x_Rays = selectedXRays.joinToString(", "),
                additionalNotes = "additionalNotes",
                doctorId = preference.getPrefVal(this).getString("docID", "") ?: "",
                patientId = preference.getPrefVal(this).getString("patientID", "") ?: "",

                )
            Log.d("abdo", "medicinDesription $medicinDesription")
            viewModel.addMedicineDescription("Bearer $token", medicinDesription)
            viewModel.addMedicin.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    Log.d("abdo", "medicine added successfully")
                    startActivity(Intent(this, HomeActivity::class.java))
                    Toast.makeText(this, "added successfully", Toast.LENGTH_SHORT).show()

                }
            })

        }






        nationalId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nationalID = s.toString().toIntOrNull()
                Log.d("test", "nationalID $nationalID")

                val patientData = body.find { it.nationalID == nationalID.toString() }

                if (patientData != null) {
                    val patientID = patientData.id
                    preference.setPrefVal(applicationContext, "patientID",patientID)
                    Log.d("abdo", "patientID $patientID")
                    userName.setText(patientData.userName)
                    phoneNumber.setText(patientData.phoneNumber)
                    mail.setText(patientData.email)
                    address.setText(patientData.address)
                } else {
                    userName.setText("")
                    phoneNumber.setText("")
                    mail.setText("")
                    address.setText("")
                }
            }
        })
    }

    fun AddDiagnosis() {
        // Initialize the Spinner with a list of items
        diagnosisAlertBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose the Appropriate Diagnosis")
            builder.setIcon(R.drawable.ic_icon_list)
            val diagnosisItems =
                arrayOf(
                    "Hypertension",
                    "Congestive heart failure",
                    "Diabetes mellitus",
                    "Back pain",
                    "Sore throat"
                )

            val checkItem = booleanArrayOf(false, false, false, false, false)
            builder.setMultiChoiceItems(diagnosisItems, checkItem) { dialog, which, isChecked ->
                //check which item is selected
                checkItem[which] = isChecked
            }
            builder.setPositiveButton("Ok") { dialog, which ->

                for (i in diagnosisItems.indices) {
                    val checked = checkItem[i]
                    if (checked) {
                        val selectedItem = diagnosisItems[i]
                        selectedDiagnosisList.add(selectedItem)
                        adapter4.notifyDataSetChanged()
                    }
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    fun AddMedicin() {
        val medicinItemsWithNotes = mapOf(
            "Acetylsalicylic Acid(300mg)" to "200 tablets Available",
            "Diclofenac sodium(25mg)" to "1000 tablets Available",
            "Diclofenac sodium(50mg)" to "1200 tablets Available",
            "Diclofenac sodium(75mg)" to "290 tablets Available",
            "Diclofenac sodium(100mg)" to "400 tablets Available"
        )

        val medicinItems = medicinItemsWithNotes.keys.toTypedArray()
        val medicinCheckItem = BooleanArray(medicinItems.size) { false }

        val medicinBuilder = AlertDialog.Builder(this)
        medicinBuilder.setTitle("Choose the Appropriate Medicine")
        medicinBuilder.setIcon(R.drawable.ic_icon_list)

        // Create an array to display items with notes
        val medicinItemsWithNotesArray = medicinItems.map { item ->
            val note = medicinItemsWithNotes[item]
            "$item\n$note" // Combine the item and note
        }.toTypedArray()



        // Attach a click listener to the button
        MedicinAlertBtn.setOnClickListener {
            medicinBuilder.setMultiChoiceItems(
                medicinItemsWithNotesArray,
                medicinCheckItem
            ) { dialog, which, isChecked ->
                // Check which item is selected
                medicinCheckItem[which] = isChecked
            }

            medicinBuilder.setPositiveButton("Ok") { dialog, which ->
                for (i in medicinItems.indices) {
                    val checked = medicinCheckItem[i]
                    if (checked) {
                        val medicinSelectedItems = medicinItems[i]
                        selectedMedicinList.add(medicinSelectedItems)
                    }
                }
                adapter3.notifyDataSetChanged()
            }

            medicinBuilder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            val dialog: AlertDialog = medicinBuilder.create()
            dialog.show()
        }
    }


    fun AddAnalysis() {
        AnalysisAlertButton.setOnClickListener {
            val analysisBuilder = AlertDialog.Builder(this)
            analysisBuilder.setTitle("Choose the Appropriate Analysis")
            analysisBuilder.setIcon(R.drawable.ic_icon_list)
            val analysisItems =
                arrayOf("Analysis 1", "Analysis 2", "Analysis 3", "Analysis 4", "Analysis 5")

            val analysisCheckItem = booleanArrayOf(false, false, false, false, false)
            analysisBuilder.setMultiChoiceItems(
                analysisItems,
                analysisCheckItem
            ) { dialog, which, isChecked ->
                //check which item is selected
                analysisCheckItem[which] = isChecked
            }
            analysisBuilder.setPositiveButton("Ok") { dialog, which ->
                for (i in analysisItems.indices) {
                    val checked = analysisCheckItem[i]
                    if (checked) {
                        val analysisSelectedItems = analysisItems[i]
                        selectedAnalysisList.add(analysisSelectedItems)
                        adapter2.notifyDataSetChanged()
                    }
                }
            }
            analysisBuilder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()

            }
            val dialog: AlertDialog = analysisBuilder.create()
            dialog.show()
        }
    }

    fun AddXRays() {
        XRayAlertButton.setOnClickListener {
            val XRayBuilder = AlertDialog.Builder(this)
            XRayBuilder.setTitle("Choose the Appropriate X-Ray(s)")
            XRayBuilder.setIcon(R.drawable.ic_icon_list)
            val XRayItems =
                arrayOf("X-Ray 1", "X-Ray 2", "X-Ray 3", "X-Ray 4", "X-Ray 5")

            val XRayCheckItem = booleanArrayOf(false, false, false, false, false)
            XRayBuilder.setMultiChoiceItems(XRayItems, XRayCheckItem) { dialog, which, isChecked ->
                //check which item is selected
                XRayCheckItem[which] = isChecked
            }
            XRayBuilder.setPositiveButton("Ok") { dialog, which ->
                for (i in XRayItems.indices) {
                    val checked = XRayCheckItem[i]
                    if (checked) {
                        val XRaySelectedItems = XRayItems[i]
                        selectedXRayList.add(XRaySelectedItems)
                        adapter5.notifyDataSetChanged()
                    }
                }
            }
            XRayBuilder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()

            }
            val dialog: AlertDialog = XRayBuilder.create()
            dialog.show()
        }
    }
}