package com.example.ramazanay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.Adapter

class CitySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)

        // Türkiye'deki bazı büyük şehirler
        val cities = arrayOf(
            "Istanbul", "Ankara", "Izmir", "Bursa", "Adana",
            "Antalya", "Konya", "Gaziantep","Elazığ","Aydın", "Mersin", "Kayseri",
            "Eskisehir", "Diyarbakir", "Samsun", "Denizli", "Sakarya",
        )

        val listView = findViewById<ListView>(R.id.listCities)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        listView.adapter = adapter
//listView.setOnItemClickListener { _, _, position, _ -> ... }: ListView'deki herhangi bir
// öğeye tıklandığında çalışacak fonksiyonu tanımlar. Bu, Kotlin'deki lambda ifadesi sözdizimini kullanır.
        //Kotlin'de, kullanılmayan parametreleri _ ile belirtmek yaygın bir uygulamadır
     /*   listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = cities[position]
                val resultIntent = Intent().apply {
                    putExtra("city", selectedCity)
                    putExtra("country", "TR")
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })
      */


        listView.setOnItemClickListener { _, _, position, _ ->
             // Tıklanan şehri belirler position tıklana indeksi belirler
             val selectedCity = cities[position]
             val resultIntent = Intent().apply {
                 putExtra("city", selectedCity)
                 putExtra("country", "TR") // Şimdilik sadece Türkiye
             }
             setResult(Activity.RESULT_OK, resultIntent)
             finish()
         }
         
    }
}