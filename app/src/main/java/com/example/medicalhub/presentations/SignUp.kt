package com.example.medicalhub.presentations

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.medicalhub.MainActivity
import com.example.medicalhub.MainViewModel
import com.example.medicalhub.MainViewModelFactory
import com.example.medicalhub.R
import com.example.medicalhub.repository.Repository
import com.google.android.material.textfield.TextInputEditText
import com.model.SignupDoctorBody
import com.model.SignupPatientBody

class SignUp : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var SignUpBtn: Button
    lateinit var userName: EditText
    lateinit var mailAddress: EditText
    lateinit var address: EditText
    lateinit var phoneNumber: EditText
    lateinit var passwordEditText: TextInputEditText
    lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var radioGroup: RadioGroup
    private var selectedText: String? = null
    private lateinit var Specialization: EditText
    private lateinit var NationalID: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        SignUpBtn = findViewById(R.id.SignUpBtn)
        userName = findViewById(R.id.UserName)
        mailAddress = findViewById(R.id.MailAddress)
        address = findViewById(R.id.address)
        phoneNumber = findViewById(R.id.PhoneNumber)
        passwordEditText = findViewById(R.id.Password2)
        confirmPasswordEditText = findViewById(R.id.ConfirmPassword2)
        radioGroup = findViewById(R.id.radioGroup)
        Specialization = findViewById(R.id.Specialization)
        NationalID = findViewById(R.id.NationalID)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Get the checked radio button
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            // Get the text of the selected radio button
            selectedText = selectedRadioButton.text.toString()
            // Toggle visibility based on the selected option
            when (selectedText) {
                "Doctor" -> {
                    Specialization.visibility = View.VISIBLE
                    NationalID.visibility = View.GONE
                }
                "Patient" -> {
                    Specialization.visibility = View.GONE
                    NationalID.visibility = View.VISIBLE
                }
                else -> {
                    Specialization.visibility = View.GONE
                    NationalID.visibility = View.GONE
                }
            }

        }

        SignUpBtn.setOnClickListener {
            if (selectedText == "Doctor"){
            val loginDoctor = SignupDoctorBody(
                userName = userName.text.toString(),
                email = mailAddress.text.toString(),
                address = address.text.toString(),
                phone = phoneNumber.text.toString(),
                password = passwordEditText.text.toString(),
                confirmPassword = confirmPasswordEditText.text.toString(),
                Specialization = Specialization.text.toString()
            )

            if (validateInput()) {
                if (isNetworkAvailable()) {
                    viewModel.doctorSignUp(loginDoctor)
                    startActivity(Intent(this, MainActivity::class.java))
                    showToastMessage("Sign up Successfully as Doctor")
                } else {
                    showToastMessage("Please enable Internet Connection")
                }
            }
            }
            else if (selectedText == "Patient"){
                val loginPatient = SignupPatientBody(
                    userName = userName.text.toString(),
                    email = mailAddress.text.toString(),
                    address = address.text.toString(),
                    phone = phoneNumber.text.toString(),
                    password = passwordEditText.text.toString(),
                    confirmPassword = confirmPasswordEditText.text.toString(),
                    nationalID = NationalID.text.toString()
                )

                if (validateInput()) {
                    if (isNetworkAvailable()) {
                        viewModel.patientSignUp(loginPatient)
                        startActivity(Intent(this, MainActivity::class.java))
                        showToastMessage("Sign up Successfully as Patient")
                    } else {
                        showToastMessage("Please enable Internet Connection")
                    }
                }
            }
            else{
                showToastMessage("Please Choose Doctor or Patient")
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun validateInput(): Boolean {
        val passwordText = passwordEditText.text.toString()
        val confirmPasswordText = confirmPasswordEditText.text.toString()
        Log.d("abdo", "password $passwordText")
        Log.d("abdo", "confirm $confirmPasswordText")
        return when {

            userName.text.toString().isEmpty() -> {
                showToast(R.string.enter_user_name)
                false
            }

            mailAddress.text.toString().isEmpty() -> {
                showToast(R.string.enter_mail_address)
                false
            }

            address.text.toString().isEmpty() -> {
                showToast(R.string.enter_address)
                false
            }

            phoneNumber.text.toString().isEmpty() -> {
                showToast(R.string.enter_phone_number)
                false
            }

            passwordText.isEmpty() -> {
                showToast(R.string.enter_password)

                false
            }

            passwordText != confirmPasswordText-> {
                showToast(R.string.password_not_match)

                false
            }

            !isPasswordValid(passwordText) -> {
                showToast(R.string.invalid_password)
                false
            }

            else -> true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+=|<>?{}\\[\\]~-]).{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}