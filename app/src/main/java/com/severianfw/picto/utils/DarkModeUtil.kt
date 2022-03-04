package com.severianfw.picto.utils

import android.content.res.Configuration
import android.content.res.Resources

object DarkModeUtil {

    fun isDarkModeActive(resources: Resources): Boolean {
        val currentDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentDarkMode == Configuration.UI_MODE_NIGHT_YES
    }
}