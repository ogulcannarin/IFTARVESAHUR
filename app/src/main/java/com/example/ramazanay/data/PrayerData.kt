package com.example.ramazanay.data
//Bu sınıf namaz vakitlerini ve tarih ile ilgilli bilgileri içerir
data class PrayerData(val timings: PrayerTimings, // Namaz vakitleri tutar
                      val date: DateInfo  // Namaz vakitleri ilgilli ait tarih bilgilerni içerir
)
