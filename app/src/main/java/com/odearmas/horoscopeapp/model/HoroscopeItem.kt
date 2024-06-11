package com.odearmas.horoscopeapp.model

import androidx.core.content.ContextCompat.getString
import com.odearmas.horoscopeapp.R

enum class HoroscopeItem(val id:String, val zodiacName: Int, val date: Int, val logo:Int) {
    ARIES("aries", R.string.horoscopeitem_name_aries, R.string.horoscopeitem_dates_aries, R.drawable.ic_aries),
    TAURUS("taurus", R.string.horoscopeitem_name_taurus,R.string.horoscopeitem_dates_taurus,R.drawable.ic_taurus),
    GEMINI("gemini", R.string.horoscopeitem_name_gemini,R.string.horoscopeitem_dates_gemini,R.drawable.ic_gemini),
    CANCER("cancer", R.string.horoscopeitem_name_cancer,R.string.horoscopeitem_dates_cancer,R.drawable.ic_cancer),
    LEO("leo", R.string.horoscopeitem_name_leo,R.string.horoscopeitem_dates_leo,R.drawable.ic_leo),
    VIRGO("virgo", R.string.horoscopeitem_name_virgo,R.string.horoscopeitem_dates_virgo,R.drawable.ic_virgo),
    LIBRA("libra", R.string.horoscopeitem_name_libra,R.string.horoscopeitem_dates_libra,R.drawable.ic_libra),
    SCORPIO("scorpio", R.string.horoscopeitem_name_scorpio,R.string.horoscopeitem_dates_scorpio,R.drawable.ic_scorpio),
    SAGITTARIUS("sagittarius", R.string.horoscopeitem_name_sagittarius,R.string.horoscopeitem_dates_sagittarius,R.drawable.ic_sagittarius),
    CAPRICORN("capricorn", R.string.horoscopeitem_name_capricorn,R.string.horoscopeitem_dates_capricorn,R.drawable.ic_capricorn),
    AQUARIUS("aquarius", R.string.horoscopeitem_name_aquarius,R.string.horoscopeitem_dates_aquarius,R.drawable.ic_aquarius),
    PISCES("pisces", R.string.horoscopeitem_name_pisces,R.string.horoscopeitem_dates_pisces,R.drawable.ic_pisces)
}