package com.example.network.logging

object NetworkLoggers {
    private val loggers = mutableListOf<ApiCallLogger>()

    fun register(logger: ApiCallLogger) {
        if (loggers.none { it::class == logger::class }) {
            loggers.add(logger)
        }
    }

    internal fun dispatch(log: ApiCallLog) {
        loggers.forEach {
            runCatching {
                it.onLog(log)
            }
        }
    }
}
