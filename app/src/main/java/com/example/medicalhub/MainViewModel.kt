package com.example.medicalhub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalhub.api.RetrofitInstance
import com.example.medicalhub.repository.Repository
import com.model.AllPateintData
import com.model.BookDate
import com.model.DoctorData
import com.model.DoctorDataEditResponse
import com.model.DoctorNewData
import com.model.GetAllDoctors
import com.model.LoginDoctorBody
import com.model.LoginDoctorBodyResponse
import com.model.LoginPatientBody
import com.model.LoginPatientBodyResponse
import com.model.MedicinDescription
import com.model.PUTDoctorWorkingDaysOfWeek
import com.model.PateintData
import com.model.PatientDataEditResponse
import com.model.PatientNewData
import com.model.ResponseBookDate
import com.model.SignUpDoctorBodyResponse
import com.model.SignUpPatientBodyResponse
import com.model.SignupDoctorBody
import com.model.SignupPatientBody
import com.model.SortedTimeInterval
import com.model.StructuredWorkingTimes
import com.model.allDaysWithID
import com.model.allDrRosheta
import com.model.medicinDescriptionResponse
import com.model.patientRosheta
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel(private val repository: Repository) : ViewModel() {
    // LiveData to hold the response from the API call
    var userDoctorSignUp: MutableLiveData<Response<SignUpDoctorBodyResponse>> = MutableLiveData()
    var userPatientSignUp: MutableLiveData<Response<SignUpPatientBodyResponse>> = MutableLiveData()
    var userDoctorLogin: MutableLiveData<Response<LoginDoctorBodyResponse>> = MutableLiveData()
    var userPatientLogin: MutableLiveData<Response<LoginPatientBodyResponse>> = MutableLiveData()
    var getDocData: MutableLiveData<Response<DoctorData>> = MutableLiveData()
    var getPatData: MutableLiveData<Response<PateintData>> = MutableLiveData()
    var getAllPatients: MutableLiveData<Response<List<AllPateintData>>> = MutableLiveData()
    var editDocData: MutableLiveData<Response<DoctorDataEditResponse>> = MutableLiveData()
    var editPatData: MutableLiveData<Response<PatientDataEditResponse>> = MutableLiveData()
    var addMedicin: MutableLiveData<Response<medicinDescriptionResponse>> = MutableLiveData()
    var getRosheta: MutableLiveData<Response<List<patientRosheta>>> = MutableLiveData()
    var getalldrRosheta: MutableLiveData<Response<List<allDrRosheta>>> = MutableLiveData()
    var postDocWorkingTime: MutableLiveData<Response<Boolean>> = MutableLiveData()
    var editDocWorkingTime: MutableLiveData<Response<allDaysWithID>> = MutableLiveData()
    var getAllDaysbiID: MutableLiveData<Response<List<allDaysWithID>>> = MutableLiveData()
    var getIntervalTimes: MutableLiveData<Response<SortedTimeInterval>> = MutableLiveData()
    var bookPatient: MutableLiveData<Response<ResponseBookDate>> = MutableLiveData()
    var getallactiveDr: MutableLiveData<Response<List<GetAllDoctors>>> = MutableLiveData()


    fun doctorSignUp(loginBody: SignupDoctorBody) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.doctorSignUp(loginBody)
            // Setting the response data in the LiveData for observation by the UI
            userDoctorSignUp.value = response1
        }
    }

    fun patientSignUp(loginBody: SignupPatientBody) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.patientSignUp(loginBody)
            // Setting the response data in the LiveData for observation by the UI
            userPatientSignUp.value = response1
        }
    }

    fun doctorLogin(loginBody: LoginDoctorBody) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.doctorLogin(loginBody)
            // Setting the response data in the LiveData for observation by the UI
            userDoctorLogin.value = response1
        }
    }

    fun patientLogin(loginBody: LoginPatientBody) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.patientLogin(loginBody)
            // Setting the response data in the LiveData for observation by the UI
            userPatientLogin.value = response1
        }
    }

    fun getDoctorData(id: String, authorization: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getDoctorData(id, authorization)
            // Setting the response data in the LiveData for observation by the UI
            getDocData.value = response1
        }
    }

    fun getPatientData(id: String, authorization: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getPatientData(id, authorization)
            // Setting the response data in the LiveData for observation by the UI
            getPatData.value = response1
        }
    }

    fun EditDoctorData(id: String, doctorNewData: DoctorNewData, authorization: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.EditDoctorData(id, doctorNewData, authorization)
            // Setting the response data in the LiveData for observation by the UI
            editDocData.value = response1
        }
    }

    fun EditPatientData(id: String, patientNewData: PatientNewData, authorization: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.EditPatientData(id, patientNewData, authorization)
            // Setting the response data in the LiveData for observation by the UI
            editPatData.value = response1
        }
    }

    fun getAllPatient(authorization: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getAllPatient(authorization)
            // Setting the response data in the LiveData for observation by the UI
            getAllPatients.value = response1
        }
    }

    fun addMedicineDescription(authorization: String, medicinDescription : MedicinDescription) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.addMedicineDescription(authorization, medicinDescription)
            // Setting the response data in the LiveData for observation by the UI
            addMedicin.value = response1
        }
    }

    fun getAllRoshetaByPatientID(authorization: String, id: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getAllRoshetaByPatientID(authorization, id)
            // Setting the response data in the LiveData for observation by the UI
            getRosheta.value = response1
        }
    }

    fun getAllRoshetaByDrID(authorization: String, id: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getAllRoshetaByDrID(authorization, id)
            // Setting the response data in the LiveData for observation by the UI
            getalldrRosheta.value = response1
        }
    }

    fun PostDoctorWorkingDaysOfWeek(authorization: String, daysOfWeek: StructuredWorkingTimes) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.PostDoctorWorkingDaysOfWeek(authorization, daysOfWeek)
            // Setting the response data in the LiveData for observation by the UI
            postDocWorkingTime.value = response1
        }
    }

    fun EditDoctorWorkingDaysOfWeek(authorization: String, daysOfWeek: PUTDoctorWorkingDaysOfWeek) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.EditDoctorWorkingDaysOfWeek(authorization, daysOfWeek)
            // Setting the response data in the LiveData for observation by the UI
            editDocWorkingTime.value = response1
        }
    }

    fun getAllDaysOfWeekByDrID(authorization: String, id: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getAllDaysOfWeekByDrID(authorization, id)
            // Setting the response data in the LiveData for observation by the UI
            getAllDaysbiID.value = response1
        }
    }

    fun getAllStoredWorkingTimes(authorization: String, id: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getAllStoredWorkingTimes(authorization, id)
            // Setting the response data in the LiveData for observation by the UI
            getIntervalTimes.value = response1
        }
    }

    fun bookDate(authorization: String,  bookNewDate: BookDate) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.bookDate(authorization, bookNewDate)
            // Setting the response data in the LiveData for observation by the UI
            bookPatient.value = response1
        }
    }

    fun getAllDoctors(authorization: String) {
        // Launching a coroutine within the viewModelScope to handle asynchronous operations
        viewModelScope.launch {
            // Calling the getPost function in the repository to fetch post data
            val response1 = repository.getAllDoctors(authorization)
            // Setting the response data in the LiveData for observation by the UI
            getallactiveDr.value = response1
        }
    }

}