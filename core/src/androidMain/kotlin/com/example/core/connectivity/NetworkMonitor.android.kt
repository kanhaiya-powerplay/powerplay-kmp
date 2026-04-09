package com.example.core.connectivity

import android.content.Context
import android.net.ConnectivityManager
import com.example.core.AppProvider

actual object NetworkMonitor {

    actual fun isOnline(): Boolean {

        return try {
            val cm = AppProvider.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = cm.activeNetwork ?: return false
            val capabilities = cm.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            false
        }
    }
}