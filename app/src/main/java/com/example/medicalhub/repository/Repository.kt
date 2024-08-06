package com.example.medicalhub.repository

import com.example.medicalhub.api.RetrofitInstance
import com.model.AllPateintData
import com.model.DaysOfWeek
import com.model.DoctorData
import com.model.DoctorDataEditResponse
import com.model.DoctorNewData
import com.model.LoginDoctorBody
import com.model.LoginDoctorBodyResponse
import com.model.LoginPatientBody
import com.model.LoginPatientBodyResponse
import com.model.MedicinDescription
import com.model.PateintData
import com.model.PatientDataEditResponse
import com.model.PatientNewData
import com.model.SignUpDoctorBodyResponse
import com.model.SignUpPatientBodyResponse
import com.model.SignupDoctorBody
import com.model.SignupPatientBody
import com.model.allDrRosheta
import com.model.medicinDescriptionResponse
import com.model.patientRosheta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

class Repository {

    suspend fun doctorSignUp(loginBody : SignupDoctorBody) : Response<SignUpDoctorBodyResponse>{
        return RetrofitInstance.api.doctorSignUp(loginBody)
    }

    suspend fun patientSignUp(loginBody : SignupPatientBody) : Response<SignUpPatientBodyResponse>{
        return RetrofitInstance.api.patientSignUp(loginBody)
    }

    suspend fun doctorLogin(loginBody : LoginDoctorBody) : Response<LoginDoctorBodyResponse>{
        return RetrofitInstance.api.doctorLogin(loginBody)
    }

    suspend fun patientLogin(loginBody : LoginPatientBody) : Response<LoginPatientBodyResponse>{
        return RetrofitInstance.api.patientLogin(loginBody)
    }

    suspend fun getDoctorData(id: String, authorization: String) : Response<DoctorData>{
        return RetrofitInstance.api.getDoctorData(id, authorization)
    }

    suspend fun getPatientData(id: String, authorization: String) : Response<PateintData>{
        return RetrofitInstance.api.getPatientData(id, authorization)
    }

    suspend fun EditDoctorData(id: String, doctorNewData: DoctorNewData, authorization: String) : Response<DoctorDataEditResponse>{
        return RetrofitInstance.api.EditDoctorData(id, doctorNewData, authorization)
    }

    suspend fun EditPatientData(id: String, patientNewData: PatientNewData, authorization: String) : Response<PatientDataEditResponse>{
        return RetrofitInstance.api.EditPatientData(id, patientNewData, authorization)
    }

    suspend fun getAllPatient(authorization: String) : Response<List<AllPateintData>>{
        return RetrofitInstance.api.getAllPatient(authorization)
    }

    suspend fun addMedicineDescription(authorization: String, medicinDescription : MedicinDescription) : Response<medicinDescriptionResponse>{
        return RetrofitInstance.api.addMedicineDescription(authorization, medicinDescription)
    }

    suspend fun getAllRoshetaByPatientID(authorization: String, id: String) : Response<List<patientRosheta>>{
        return RetrofitInstance.api.getAllRoshetaByPatientID(authorization, id)
    }

    suspend fun getAllRoshetaByDrID(authorization: String, id: String) : Response<List<allDrRosheta>>{
        return RetrofitInstance.api.getAllRoshetaByDrID(authorization, id)
    }

    suspend fun PostDoctorWorkingDaysOfWeek(authorization: String, daysOfWeek: DaysOfWeek) : Response<Boolean>{
        return RetrofitInstance.api.PostDoctorWorkingDaysOfWeek(authorization, daysOfWeek)
    }

}