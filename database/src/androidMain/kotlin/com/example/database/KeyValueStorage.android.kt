package com.example.database

import android.content.Context
import com.example.core.AppProvider


actual object KeyValueStorage {

    val sharedPreferences = AppProvider.applicationContext.getSharedPreferences("powerplay", Context.MODE_PRIVATE)!!

    actual inline operator fun <reified T> get(key: StorageKeys): T?{

        val _key = key.name
        sharedPreferences.apply {
            return when (contains(_key)) {
                true -> when (T::class) {
                    Int::class -> getInt(_key, 0)
                    Long::class -> getLong(_key, 0L)
                    Float::class -> getFloat(_key, 0f)
                    String::class -> getString(_key, null)
                    Boolean::class -> getBoolean(_key, false)
                    else -> null
                }

                false -> null
            } as? T
        }
    }

    actual operator fun set(key: StorageKeys, value: Any?){

        val _key = key.name
        sharedPreferences.edit().apply {
            when (value) {
                null -> remove(_key)
                else -> when (value) {
                    is Int -> putInt(_key, value)
                    is Long -> putLong(_key, value)
                    is Float -> putFloat(_key, value)
                    is String -> putString(_key, value)
                    is Boolean -> putBoolean(_key, value)
                    else -> Unit
                }
            }
        }?.apply()
    }
}