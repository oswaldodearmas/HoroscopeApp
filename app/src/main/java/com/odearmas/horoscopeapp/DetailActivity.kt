package com.odearmas.horoscopeapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.odearmas.horoscopeapp.model.HoroscopeItem

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val zodiacId = intent.getStringExtra("horoscope_id")
        if (zodiacId != null) {
            val zodiac = HoroscopeItem.fromId(zodiacId)
            //val zodiac = zodiacId?.let { HoroscopeItem.fromId(it) }
            if (zodiac != null) {
                findViewById<TextView>(R.id.selected_name_textView).text =
                    getString(zodiac.zodiacName)
                findViewById<TextView>(R.id.selected_date_textView).text = getString(zodiac.date)
                findViewById<ImageView>(R.id.selected_icon_imageView).setImageResource(zodiac.logo)
            }
        }

    }
}