package com.example.network

import com.example.network.apiClientConfigurers.APIClientConfigurer
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

/**
 * iOS actual implementation of createHttpClient.
 *
 * Uses Ktor's Darwin engine which wraps Apple's native NSURLSession.
 * This is the correct engine for iOS — it respects iOS network policies,
 * background transfer rules, and App Transport Security (ATS).
 *
 * No OkHttp on iOS — the equivalent observability hook for iOS would be
 * a Ktor HttpSendPipeline/HttpReceivePipeline interceptor, which can be
 * added here in the future when iOS debug tooling is needed.
 *
 * All Ktor-level configurers (JSON, headers, etc.) are applied the same
 * way as on Android.
 */
actual fun createHttpClient(configurers: List<APIClientConfigurer>): HttpClient {
    return HttpClient(Darwin) {
        configurers.forEach { it.apply(this) }
    }
}
