package com.example.network.logging

abstract class ApiCallLogger {
    abstract fun onLog(log: ApiCallLog)
}