package com.example.database

import platform.Foundation.NSUserDefaults

actual object KeyValueStorage {

    actual inline operator fun <reified T>get(key: StorageKeys): T?{
        return NSUserDefaults.standardUserDefaults.objectForKey(key.name) as? T
    }

    actual operator fun set(key: StorageKeys, value: Any?) {
        when(value) {
            null -> NSUserDefaults.standardUserDefaults.removeObjectForKey(key.name)
            else -> NSUserDefaults.standardUserDefaults.setObject(value, key.name)
        }
    }
}