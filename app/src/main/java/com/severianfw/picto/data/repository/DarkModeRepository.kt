package com.severianfw.picto.data.repository

interface DarkModeRepository {

    fun isDarkModeActive(): Boolean
    fun setDarkModeStatus(isDarkMode: Boolean)
}