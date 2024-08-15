package com.model

import java.util.Date


data class SignupDoctorBody(
    val userName: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
    val Specialization: String
)

//signUp body response
data class SignUpDoctorBodyResponse(
    val userName: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
    val Specialization: String
)

data class SignupPatientBody(
    val userName: String,
    val nationalID: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
)

//signUp body response
data class SignUpPatientBodyResponse(
    val userName: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
    val nationalID: String,
)

//Login Doctor Body
data class LoginDoctorBody(
    val userName: String,
    val password: String,
    val rememberMe: Boolean,

)

//Login Doctor body response
data class LoginDoctorBodyResponse(
    val token: String,
    val expiration: String,
    val userName: String,
    val id: String,
)


//Login Patient Body
data class LoginPatientBody(
    val nationalID: String,
    val password: String,
    val rememberMe: Boolean,
)

//Login Patient response
data class LoginPatientBodyResponse(
    val token: String,
    val expiration: String,
    val userName: String,
    val id: String,
)

//Login Patient Body
data class DoctorData(
    val id: String,
    val userName: String,
    val specialization: String?,
    val email: String,
    val phoneNumber: String,
    val address: String,

)


//Doctor editdata
data class DoctorNewData(
    val userName: String,
    val specialization: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
)

//Doctor editdata Response
data class DoctorDataEditResponse(
    val userName: String,
    val specialization: String?,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
    )


//Patient edit data
data class PatientNewData(
    val userName: String,
    val nationalID: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,
)

//Patient edit data response
data class PatientDataEditResponse(
    val userName: String,
    val nationalID: String?,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val confirmPassword: String,

    )


//Get Patient Data Response
data class PateintData(
    val id: String,
    val nationalID: String,
    val userName: String,
    val email: String,
    val address: String,
    val phoneNumber: String,
    val chronicDiseases: String?,
    val previousOperations: String?,
    val allergies: String?,
    val currentMedications: String?,
    val comments: String?,
    )


data class AllPateintData(
    val id: String,
    val nationalID: String,
    val userName: String,
    val email: String,
    val address: String,
    val phone: String,
    val password: String,
    val medicineDescriptions: String?,
    val patientBooking: String?,
    val patientCode: Int,
    val chronicDiseases: String?,
    val previousOperations: String?,
    val allergies: String?,
    val currentMedications: String?,
    val comments: String?,
)


//Get Patient Data Response
data class MedicinDescription(
    val diagnosis: String,
    val medicine: String,
    val analysis: String,
    val x_Rays: String,
    val additionalNotes: String,
    val doctorId: String,
    val patientId: String,
    val patientCode: String,

    )

//Get All Patients data

data class medicinDescriptionResponse(
    val diagnosis: String,
    val medicine: String,
    val analysis: String,
    val x_Rays: String,
    val additionalNotes: String,
    val doctorId: String,
    val patientId: String,

)


data class patientRosheta(
    val id: String,
    val diagnosis: String,
    val medicine: String,
    val analysis: String,
    val x_Rays: String,
    val additionalNotes: String,
    val doctorId: String,
    val patientId: String,
    val doctor: Any?,
    val patient: Any?,
    val createdOn: String,
    val createdBy: Any?,
    val updatedOn: String?,
    val updatedBy: Any?
)


data class allDrRosheta(
    val id: String,
    val patientCode: Int,
    val diagnosis: String,
    val medicine: String,
    val analysis: String,
    val x_Rays: String,
    val additionalNotes: String,
    val doctorId: String,
    val patientId: String,
    val doctor: Any?,
    val patient: Any?,
    val createdOn: String,
    val createdBy: Any?,
    val updatedOn: String?,
    val updatedBy: Any?
)


//Post Days of week
data class StructuredWorkingTimes(
    var sunDayFrom: String = "",
    var sunDayTo: String = "",
    var monDayFrom: String = "",
    var monDayTo: String = "",
    var tuesDayFrom: String = "",
    var tuesDayTo: String = "",
    var wednesDayFrom: String = "",
    var wednesDayTo: String = "",
    var thursDayFrom: String = "",
    var thursDayTo: String = "",
    var friDayFrom: String = "",
    var friDayTo: String = "",
    var saturDayFrom: String = "",
    var saturDayTo: String = "",
    var doctorId: String = ""
)

//Edit Days of week
data class PUTDoctorWorkingDaysOfWeek(
    var id: Int,
    var sunDayFrom: String = "",
    var sunDayTo: String = "",
    var monDayFrom: String = "",
    var monDayTo: String = "",
    var tuesDayFrom: String = "",
    var tuesDayTo: String = "",
    var wednesDayFrom: String = "",
    var wednesDayTo: String = "",
    var thursDayFrom: String = "",
    var thursDayTo: String = "",
    var friDayFrom: String = "",
    var friDayTo: String = "",
    var saturDayFrom: String = "",
    var saturDayTo: String = "",
    var doctorId: String = "",
    var doctor:String?
)
//GetAllDaysOfTheWeekByDoctorId with ID
data class allDaysWithID(
    var id: Int,
    var sunDayFrom: String = "",
    var sunDayTo: String = "",
    var monDayFrom: String = "",
    var monDayTo: String = "",
    var tuesDayFrom: String = "",
    var tuesDayTo: String = "",
    var wednesDayFrom: String = "",
    var wednesDayTo: String = "",
    var thursDayFrom: String = "",
    var thursDayTo: String = "",
    var friDayFrom: String = "",
    var friDayTo: String = "",
    var saturDayFrom: String = "",
    var saturDayTo: String = "",
    var doctorId: String = ""
)

data class BookDate(
    val patientId: String,
    val doctorTimeIntervalId: Int
)

data class ResponseBookDate(
    val patientId: String,
    val doctorTimeIntervalId: Int
)

data class GetAllDoctors(
    val id: String,
    val userName: String,
    val specialization: String?,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val password: String,
    val medicineDescriptions: Any?, // Change Any? to the specific type if you know it
    val doctorWorkingTime: Any?,    // Change Any? to the specific type if you know it
    val doctorWorkingDaysOfWeek: Any? // Change Any? to the specific type if you know it
)
