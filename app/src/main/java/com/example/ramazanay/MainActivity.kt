package com.example.ramazanay

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ramazanay.data.PrayerTimesResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var txtIftarTime: TextView
    private lateinit var txtSahurTime: TextView
    private lateinit var txtCountdown: TextView
    private lateinit var txtCity: TextView
    private lateinit var btnChangeCity: Button
    private lateinit var sharedPreferences: SharedPreferences

    private var currentCity = "Istanbul"
    private var currentCountry = "TR"

    private val CITY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtIftarTime = findViewById(R.id.txtIftarTime)
        txtSahurTime = findViewById(R.id.txtSahurTime)
        txtCountdown = findViewById(R.id.txtCountdown)
        txtCity = findViewById(R.id.txtCity)
        btnChangeCity = findViewById(R.id.btnChangeCity)

        // SharedPreferences ile son seçilen şehri kaydetme
        sharedPreferences = getSharedPreferences("RamazanApp", MODE_PRIVATE)

        // Kaydedilmiş bir şehir varsa onu kullan
        currentCity = sharedPreferences.getString("city", "Istanbul") ?: "Istanbul"
        currentCountry = sharedPreferences.getString("country", "TR") ?: "TR"

        txtCity.text = currentCity

        // Şehir değiştir butonuna tıklanınca
        btnChangeCity.setOnClickListener {
            val intent = Intent(this, CitySelectionActivity::class.java)
            startActivityForResult(intent, CITY_REQUEST_CODE)
        }

        // Vakitleri al
        getPrayerTimes(currentCity, currentCountry)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val city = it.getStringExtra("city") ?: return
                val country = it.getStringExtra("country") ?: return

                currentCity = city
                currentCountry = country

                // Şehri SharedPreferences'a kaydet
                sharedPreferences.edit().apply {
                    putString("city", city)
                    putString("country", country)
                    apply()
                }

                // UI'ı güncelle
                txtCity.text = city

                // Yeni şehir için vakitleri getir
                getPrayerTimes(city, country)
            }
        }
    }

    private fun getPrayerTimes(city: String, country: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/") // API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(PrayerTimesApi::class.java)

        api.getPrayerTimes(city, country).enqueue(object : Callback<PrayerTimesResponse> {
            override fun onResponse(call: Call<PrayerTimesResponse>, response: Response<PrayerTimesResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { prayerTimesResponse ->
                        val iftarTime = prayerTimesResponse.data.timings.Maghrib
                        val sahurTime = prayerTimesResponse.data.timings.Fajr

                        // Ekranda gösterme
                        txtIftarTime.text = iftarTime
                        txtSahurTime.text = sahurTime

                        // İftara kalan süreyi hesaplama
                        startCountdown(iftarTime)
                    }
                } else {
                    txtIftarTime.text = "Veri çekilemedi!"
                }
            }

            override fun onFailure(call: Call<PrayerTimesResponse>, t: Throwable) {
                txtIftarTime.text = "Veri çekilemedi!"
                txtSahurTime.text = "Bağlantınızı kontrol edin."
            }
        })
    }

    private fun startCountdown(iftarTime: String) {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val now = Calendar.getInstance()
        val iftar = Calendar.getInstance()

        try {
            val iftarDate = format.parse(iftarTime)
            if (iftarDate != null) {
                val iftarHour = SimpleDateFormat("HH", Locale.getDefault()).format(iftarDate).toInt()
                val iftarMinute = SimpleDateFormat("mm", Locale.getDefault()).format(iftarDate).toInt()

                iftar.set(Calendar.HOUR_OF_DAY, iftarHour)
                iftar.set(Calendar.MINUTE, iftarMinute)
                iftar.set(Calendar.SECOND, 0) // Saniyeleri sıfırla

                // Eğer iftar vakti geçmişse, bir sonraki güne ekleyelim.
                if (iftar.before(now)) {
                    iftar.add(Calendar.DAY_OF_YEAR, 1)
                }

                val diff = iftar.timeInMillis - now.timeInMillis

                // Geri sayımı başlat
                object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val hours = millisUntilFinished / (1000 * 60 * 60)
                        val minutes = (millisUntilFinished / (1000 * 60)) % 60
                        val seconds = (millisUntilFinished / 1000) % 60
                        txtCountdown.text = "$hours:$minutes:$seconds"
                    }

                    override fun onFinish() {
                        txtCountdown.text = "İftar vakti!"
                    }
                }.start()
            }
        } catch (e: Exception) {
            txtCountdown.text = "Zaman hesaplanamadı!"
        }
    }
}