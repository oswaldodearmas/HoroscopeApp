package com.odearmas.horoscopeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.odearmas.horoscopeapp.model.HoroscopeItem

class MainActivity : AppCompatActivity() {

    val horoscopeList: List<HoroscopeItem> = listOf(
        HoroscopeItem.ARIES,
        HoroscopeItem.TAURUS,
        HoroscopeItem.GEMINI,
        HoroscopeItem.CANCER,
        HoroscopeItem.LEO,
        HoroscopeItem.VIRGO,
        HoroscopeItem.LIBRA,
        HoroscopeItem.SCORPIO,
        HoroscopeItem.SAGITTARIUS,
        HoroscopeItem.CAPRICORN,
        HoroscopeItem.AQUARIUS,
        HoroscopeItem.PISCES
    )

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.activity_main_recyclerView)
        val adapter = HoroscopeAdapter(horoscopeList)
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this,2)
    }
}
