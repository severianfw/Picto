package com.severianfw.picto.utils

import com.severianfw.picto.data.repository.DarkModeRepository
import javax.inject.Inject

class DarkModeUtil @Inject constructor(private val darkModeRepository: DarkModeRepository) {

    fun isDarkModeActive(): Boolean {
        return darkModeRepository.isDarkModeActive()
    }

    fun setDarkModeStatus(isDarkMode: Boolean) {
        darkModeRepository.setDarkModeStatus(isDarkMode)
    }
}