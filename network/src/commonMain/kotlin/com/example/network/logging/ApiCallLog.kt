package com.example.network.logging

data class ApiCallLog(
    val url: String,
    val method: String,
    val statusCode: Int,
    val statusMessage: String,
    val requestHeaders: Map<String, String>,
    val requestBody: String?,
    val responseHeaders: Map<String, String>,
    val responseBody: String?,
    val timestamp: Long,
    val durationMs: Long,
    val contentLength: Long,
    val errorMessage: String? = null,
    val source: String = "KMP",
)
