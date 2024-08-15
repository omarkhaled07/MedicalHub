package com.model

//data class to represent a day of the week
data class DayOfWeek(val name: String, var isSelected: Boolean = false,
                     var startTime: String = "Start Time",
                     var endTime: String = "End Time")


data class GetDayOfWeek(val name: String,
                        var startTime: String = "Start Time",
                        var endTime: String = "End Time")
