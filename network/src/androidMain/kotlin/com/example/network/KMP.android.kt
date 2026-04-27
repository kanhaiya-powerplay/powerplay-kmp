package com.example.network

import android.content.Context
import com.example.core.AppProvider
import com.example.core.KMPConfig

object KMP {
    fun init(context: Context, baseUrl: String) {
        AppProvider.init(context.applicationContext)
        KMPConfig.baseUrl = baseUrl
    }
}
