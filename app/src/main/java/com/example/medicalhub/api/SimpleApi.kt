package com.example.medicalhub.api


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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SimpleApi {
//ezayak ya dr ahmed
    @POST("Doctors")
    suspend fun doctorSignUp(
        @Body loginBody: SignupDoctorBody
    ): Response<SignUpDoctorBodyResponse>

    @POST("Login/DoctorLogin")
    suspend fun doctorLogin(
        @Body loginBody: LoginDoctorBody
    ): Response<LoginDoctorBodyResponse>

    @POST("Patients")
    suspend fun patientSignUp(
        @Body loginBody: SignupPatientBody
    ): Response<SignUpPatientBodyResponse>

    @POST("Login/PatientLogin")
    suspend fun patientLogin(
        @Body loginBody: LoginPatientBody
    ): Response<LoginPatientBodyResponse>

    @Headers("Content-Type: application/json")
    @GET("Doctors/{DoctorName}")
    suspend fun getDoctorData(
        @Path("DoctorName") id: String,
        @Header("Authorization") authorization: String
    ): Response<DoctorData>

    @Headers("Content-Type: application/json")
    @GET("Patients/{PatientId}")
    suspend fun getPatientData(
        @Path("PatientId") id: String,
        @Header("Authorization") authorization: String
    ): Response<PateintData>

    @Headers("Content-Type: application/json")
    @PUT("Doctors/{DoctorName}")
    suspend fun EditDoctorData(
        @Path("DoctorName") id: String,
        @Body doctorNewData: DoctorNewData,
        @Header("Authorization") authorization: String
    ): Response<DoctorDataEditResponse>


    @Headers("Content-Type: application/json")
    @PUT("Patients/{PatientId}")
    suspend fun EditPatientData(
        @Path("PatientId") id: String,
        @Body patientNewData: PatientNewData,
        @Header("Authorization") authorization: String
    ): Response<PatientDataEditResponse>


    @Headers("Content-Type: application/json")
    @GET("Patients")
    suspend fun getAllPatient(
        @Header("Authorization") authorization: String
    ): Response<List<AllPateintData>>


    @Headers("Content-Type: application/json")
    @POST("MedicineDescription")
    suspend fun addMedicineDescription(
        @Header("Authorization") authorization: String,
        @Body medicinDescription : MedicinDescription
    ): Response<medicinDescriptionResponse>

    @Headers("Content-Type: application/json")
    @GET("MedicineDescription/GetAllMedicineDescriptionByPatientId/{PatientId}")
    suspend fun getAllRoshetaByPatientID(
        @Header("Authorization") authorization: String,
        @Path("PatientId") id: String,
    ): Response<List<patientRosheta>>


    @Headers("Content-Type: application/json")
    @GET("MedicineDescription/GetAllMedicineDescriptionByDoctorId/{DoctorId}")
    suspend fun getAllRoshetaByDrID(
        @Header("Authorization") authorization: String,
        @Path("DoctorId") id: String,
    ): Response<List<allDrRosheta>>

    @Headers("Content-Type: application/json")
    @POST("DoctorWorkingDaysOfWeek")
    suspend fun PostDoctorWorkingDaysOfWeek(
        @Header("Authorization") authorization: String,
        @Body daysOfWeek: StructuredWorkingTimes
    ): Response<Boolean>

    @Headers("Content-Type: application/json")
    @PUT("DoctorWorkingDaysOfWeek")
    suspend fun EditDoctorWorkingDaysOfWeek(
        @Header("Authorization") authorization: String,
        @Body daysOfWeek: PUTDoctorWorkingDaysOfWeek
    ): Response<allDaysWithID>

    @Headers("Content-Type: application/json")
    @GET("DoctorWorkingDaysOfWeek/GetAllDaysOfTheWeekByDoctorId/{DoctorId}")
    suspend fun getAllDaysOfWeekByDrID(
        @Header("Authorization") authorization: String,
        @Path("DoctorId") id: String,
    ): Response<List<allDaysWithID>>

    @Headers("Content-Type: application/json")
    @GET("DoctorWorkingTime/{DoctorId}/AllStoredWorkingTimes")
    suspend fun getAllStoredWorkingTimes(
        @Header("Authorization") authorization: String,
        @Path("DoctorId") id: String,
    ): Response<SortedTimeInterval>

    @Headers("Content-Type: application/json")
    @POST("PatientBooking")
    suspend fun bookDate(
        @Header("Authorization") authorization: String,
        @Body bookNewDate: BookDate
    ): Response<ResponseBookDate>

    @Headers("Content-Type: application/json")
    @GET("Doctors")
    suspend fun getAllDoctors(
        @Header("Authorization") authorization: String,
    ): Response<List<GetAllDoctors>>


}

