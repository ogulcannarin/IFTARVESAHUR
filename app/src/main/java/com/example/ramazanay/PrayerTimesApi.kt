
package com.example.ramazanay

import com.example.ramazanay.data.PrayerTimesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// API arayüzü

//Bu bir api yapısı
//PrayerTimesResponse bu sınıf apiden gelen yanıtları temsil eder
//
interface PrayerTimesApi {
    @GET("v1/timingsByCity")
    fun getPrayerTimes(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int = 2 // Diyanet İşleri metodu
    ): Call<PrayerTimesResponse>
}
