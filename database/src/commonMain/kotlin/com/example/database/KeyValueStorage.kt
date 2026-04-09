package com.example.database

expect object KeyValueStorage {

    operator fun set(key: StorageKeys, value: Any?)
    inline operator fun <reified T> get(key: StorageKeys): T?
}