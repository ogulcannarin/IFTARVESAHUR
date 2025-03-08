package com.example.ramazanay.data

data class PrayerTimings( val Fajr: String, // sabah nazmazı
                          val Sunrise: String, // Güneşin doğuşu
                          val Dhuhr: String,  // Öğle namazı vakti
                          val Asr: String,   // İkindi nazmazı
                          val Sunset: String,  // Güneşin batışı
                          val Maghrib: String, // Akşam namazı
                          val Isha: String,  //Yatsı namazı
                          val Imsak: String,  // İmsak vakti
                          val Midnight: String) // Gece yarısı
