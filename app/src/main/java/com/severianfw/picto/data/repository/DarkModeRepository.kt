package com.severianfw.picto.data.repository

import android.content.SharedPreferences

interface DarkModeRepository {

    fun isDarkModeActive(): Boolean
    fun setDarkModeStatus(isDarkMode: Boolean)
}