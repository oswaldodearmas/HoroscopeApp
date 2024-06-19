package com.odearmas.horoscopeapp.data

import com.odearmas.horoscopeapp.R

enum class HoroscopeItem(
    val id: String,
    val zodiacName: Int,
    val date: Int,
    val logo: Int
) {
    ARIES(
        "aries",
        R.string.horoscopeItem_name_aries,
        R.string.horoscopeItem_dates_aries,
        R.drawable.ic_aries
    ),
    TAURUS(
        "taurus",
        R.string.horoscopeItem_name_taurus,
        R.string.horoscopeItem_dates_taurus,
        R.drawable.ic_taurus
    ),
    GEMINI(
        "gemini",
        R.string.horoscopeItem_name_gemini,
        R.string.horoscopeItem_dates_gemini,
        R.drawable.ic_gemini
    ),
    CANCER(
        "cancer",
        R.string.horoscopeItem_name_cancer,
        R.string.horoscopeItem_dates_cancer,
        R.drawable.ic_cancer
    ),
    LEO(
        "leo",
        R.string.horoscopeItem_name_leo,
        R.string.horoscopeItem_dates_leo,
        R.drawable.ic_leo
    ),
    VIRGO(
        "virgo",
        R.string.horoscopeItem_name_virgo,
        R.string.horoscopeItem_dates_virgo,
        R.drawable.ic_virgo
    ),
    LIBRA(
        "libra",
        R.string.horoscopeItem_name_libra,
        R.string.horoscopeItem_dates_libra,
        R.drawable.ic_libra
    ),
    SCORPIO(
        "scorpio",
        R.string.horoscopeItem_name_scorpio,
        R.string.horoscopeItem_dates_scorpio,
        R.drawable.ic_scorpio
    ),
    SAGITTARIUS(
        "sagittarius",
        R.string.horoscopeItem_name_sagittarius,
        R.string.horoscopeItem_dates_sagittarius,
        R.drawable.ic_sagittarius
    ),
    CAPRICORN(
        "capricorn",
        R.string.horoscopeItem_name_capricorn,
        R.string.horoscopeItem_dates_capricorn,
        R.drawable.ic_capricorn
    ),
    AQUARIUS(
        "aquarius",
        R.string.horoscopeItem_name_aquarius,
        R.string.horoscopeItem_dates_aquarius,
        R.drawable.ic_aquarius
    ),
    PISCES(
        "pisces",
        R.string.horoscopeItem_name_pisces,
        R.string.horoscopeItem_dates_pisces,
        R.drawable.ic_pisces
    );

    companion object {
        fun fromId(id: String): HoroscopeItem? {
            return entries.find { it.id == id }
        }
    }
}