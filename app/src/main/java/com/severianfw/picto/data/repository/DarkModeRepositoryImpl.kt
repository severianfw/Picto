package com.severianfw.picto.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.severianfw.picto.utils.Constant
import javax.inject.Inject

class DarkModeRepositoryImpl @Inject constructor(
    private val context: Context
) : DarkModeRepository {

    private fun getDarkModeSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(Constant.SP_DARK_MODE, Context.MODE_PRIVATE)
    }

    override fun isDarkModeActive(): Boolean {
        val sharedPref = getDarkModeSharedPreference()
        return sharedPref.getBoolean(Constant.DARK_MODE_STATUS, false)
    }

    override fun setDarkModeStatus(isDarkMode: Boolean) {
        val sharedPref = getDarkModeSharedPreference()
        with(sharedPref.edit()) {
            putBoolean(Constant.DARK_MODE_STATUS, isDarkMode)
            apply()
            commit()
        }
    }
}