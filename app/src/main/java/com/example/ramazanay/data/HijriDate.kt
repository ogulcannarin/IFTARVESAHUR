package com.example.ramazanay.data

data class HijriDate(val date: String,
                     val format: String,
                     val day: String,
                     val weekday: WeekdayInfo,
                     val month: MonthInfo,
                     val year: String,
                     val designation: Designation
)
