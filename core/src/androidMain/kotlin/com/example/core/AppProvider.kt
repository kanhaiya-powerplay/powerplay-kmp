package com.example.core

import android.content.Context

object AppProvider {
    lateinit var applicationContext: Context
        private set

    @Synchronized
    fun init(context: Context) {
        applicationContext = context
    }
}