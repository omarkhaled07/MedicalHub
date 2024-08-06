package com.example.medicalhub

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicalhub.presentations.HomeActivity
import com.example.medicalhub.presentations.SignUp
import com.example.i_freezemanager.data.SharedPrefManager
import com.example.medicalhub.presentations.HomePatientActivity
import com.example.medicalhub.repository.Repository
import com.google.android.material.textfield.TextInputLayout
import com.model.LoginDoctorBody
import com.model.LoginPatientBody

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var preference: SharedPrefManager
    lateinit var button: Button
    lateinit var signUp: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var UserNameText: EditText
    private lateinit var NationalID: EditText
    private lateinit var passText: TextInputLayout
    private var selectedText = "Doctor"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preference = SharedPrefManager(this)
        UserNameText = findViewById(R.id.mailText)
        passText = findViewById(R.id.passText)

        UserNameText.setText(preference.getPrefVal(this).getString("username", ""))
        passText.editText?.setText(preference.getPrefVal(this).getString("password", ""))
        signUp = findViewById(R.id.signUp)
        button = findViewById(R.id.button)
        radioGroup = findViewById(R.id.radioGroup)
        NationalID = findViewById(R.id.nationalID)
        // Creating a ViewModelFactory with the repository
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]



        signUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        // Set the default selection to "Doctor"
        radioGroup.check(R.id.radio_option1)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Get the checked radio button
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            // Get the text of the selected radio button
            selectedText = selectedRadioButton.text.toString()
            // Toggle visibility based on the selected option
            when (selectedText) {
                "Doctor" -> {
                    UserNameText.visibility = View.VISIBLE
                    NationalID.visibility = View.GONE
                }

                "Patient" -> {
                    UserNameText.visibility = View.GONE
                    NationalID.visibility = View.VISIBLE
                }
            }

        }

        button.setOnClickListener {
            preference.setPrefVal(this,"SelectedRadioButton",selectedText)
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "Please enable Internet Connection", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val userName = UserNameText.text.toString()
            val password = passText.editText?.text.toString()
            val nationalID = NationalID.text.toString()

            if (selectedText == "Doctor") {
                 if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please enter Email and Password", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val loginBody = LoginDoctorBody(
                    userName = userName,
                    password = password,
                    rememberMe = true
                )
                viewModel.doctorLogin(loginBody)
                viewModel.userDoctorLogin.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        preference.setPrefVal(this, "username", userName)
                        preference.setPrefVal(this, "password", password)
                        preference.setPrefVal(this, "token", response.body()?.token.toString())
                        preference.setPrefVal(this, "docID", response.body()?.id.toString())
                        Log.d("abdo", "username $userName")
                        Log.d("abdo", "doc id ${response.body()?.id.toString()}")
                        Log.d("abdo", "token ${response.body()?.token.toString()}")
                        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
//                        intent.putExtra("username", userName)
                        startActivity(intent)
                    } else {
                        Log.d("abdo", response.errorBody().toString())
                        Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }else{
                preference.setPrefVal(this,"SelectedRadioButton",selectedText)
                if (nationalID.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please enter NationalID and Password", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val loginBody = LoginPatientBody(
                    nationalID = nationalID,
                    password = password,
                    rememberMe = true
                )
                viewModel.patientLogin(loginBody)
                viewModel.userPatientLogin.observe(this, Observer { response ->
                    if (response.isSuccessful) {

                        preference.setPrefVal(this, "nationalID", nationalID)
                        preference.setPrefVal(this, "password", password)
                        preference.setPrefVal(this, "tokenPatient", response.body()?.token.toString())
                        preference.setPrefVal(this, "patID", response.body()?.id.toString())
                        Toast.makeText(this, "Login Successfully as Patient", Toast.LENGTH_SHORT).show()
                        Log.d("abdo", "patID ${response.body()?.id.toString()}")
                        Log.d("abdo", "tokenPatient ${response.body()?.token.toString()}")
                        startActivity(Intent(this, HomePatientActivity::class.java))
                    } else {
                        Log.d("abdo", response.errorBody().toString())
                        Toast.makeText(this, "Invalid NationalID or Password", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }

        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}