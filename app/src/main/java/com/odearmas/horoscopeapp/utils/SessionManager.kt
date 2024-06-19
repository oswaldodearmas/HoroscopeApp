package com.odearmas.horoscopeapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    companion object {
        const val FAVORITE_HOROSCOPE = "FAVORITE_HOROSCOPE"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("horoscope_session", Context.MODE_PRIVATE)
    }

    fun setFavoriteHoroscope(id: String) {
        sharedPreferences.edit().putString(FAVORITE_HOROSCOPE, id).apply()
    }

    fun getFavoriteHoroscope(): String? {
        return sharedPreferences.getString(FAVORITE_HOROSCOPE, null)
    }

}