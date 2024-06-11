package com.odearmas.horoscopeapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Esta clase se encarga de guardarnos la vista y no tener que inflarla de nuevo
class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView1: TextView
    val textView2: TextView
    val imageView: ImageView

    init {
        imageView = view.findViewById(R.id.item_horoscope_icon_imageView)
        textView1 = view.findViewById(R.id.item_horoscope_name_textView)
        textView2 = view.findViewById(R.id.item_horoscope_date_textView)
    }
}