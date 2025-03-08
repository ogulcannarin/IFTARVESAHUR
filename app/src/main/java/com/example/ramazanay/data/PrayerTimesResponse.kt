package com.example.ramazanay.data

data class PrayerTimesResponse(
//PrayerTimesResponse bu sınıf apiden gelen yanıtları temsil eder
    val code: Int,
    val status: String,
    val data: PrayerData // apini namaz vakitleri sırası
)
