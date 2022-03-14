package com.severianfw.picto.connectionlistener

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import javax.inject.Inject

class InternetConnectionListenerImpl @Inject constructor(
    private val context: Context,
) : InternetConnectionListener {

    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (isBuildLeastM()) {
            isConnectedBuildLeastM(connectivityManager)
        } else {
            isConnectedBuildUnderM(connectivityManager)
        }
    }

    private fun isBuildLeastM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedBuildLeastM(connectivityManager: ConnectivityManager): Boolean {
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (networkCapabilities != null) {
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
        return false
    }

    private fun isConnectedBuildUnderM(connectivityManager: ConnectivityManager): Boolean {
        try {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        } catch (e: Exception) {
            Log.d("MESSAGE", e.message.toString())
        }
        return false
    }
}