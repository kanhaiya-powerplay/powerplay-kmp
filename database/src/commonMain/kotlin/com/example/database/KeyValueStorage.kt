package com.example.database

expect object KeyValueStorage {
    inline operator fun <reified T>get(key: String): T
    operator fun set(key: String, value: Any)
}