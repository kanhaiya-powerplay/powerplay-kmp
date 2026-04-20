package com.example.network

import com.example.core.KMPAndroidConfig
import com.example.network.apiClientConfigurers.APIClientConfigurer
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

/**
 * Android actual implementation of createHttpClient.
 *
 * HOW OkHttp INJECTION WORKS
 * ──────────────────────────
 * Ktor's OkHttp engine has a special `preconfigured` property that replaces
 * Ktor's internally-created OkHttpClient with one you supply.  When the host
 * app (powerplay-fieldapp-android) passes its own OkHttpClient via
 * `initKMPForAndroid(okHttpClient = ...)`, that client already has:
 *   • ApiLogsInterceptor  — logs every request/response to the debug Room DB
 *   • Any other app-level interceptors the host app wants
 *
 * Result: every HTTP call made by the KMP library shows up automatically in
 * the fieldapp's DebugActivity → API Request list, with full cURL, timing,
 * headers, and body — without any extra code.
 *
 * RELEASE BUILDS
 * ──────────────.
 * KMPAndroidConfig.okHttpClient is null on release (the host app never sets it).
 * The `?.let { preconfigured = it }` is null-safe, so Ktor falls back to
 * creating its own default OkHttpClient — clean, no debug code ships.
 *
 * ALL CONFIGURERS STILL APPLY
 * ───────────────────────────
 * Content-negotiation (JSON), default headers (Authorization, platform, etc.)
 * are applied via the configurers list — same as before.  The injected
 * OkHttpClient only affects the transport layer, not the Ktor plugin layer.
 */
actual fun createHttpClient(configurers: List<APIClientConfigurer>): HttpClient {
    return HttpClient(OkHttp) {
        engine {
            // If the host app provided an OkHttpClient (debug/staging/preprod),
            // use it as the underlying transport.  null → Ktor default client.
            KMPAndroidConfig.okHttpClient?.let { preconfigured = it }
        }
        // Apply all Ktor-level plugins (JSON, headers, etc.)
        configurers.forEach { it.apply(this) }
    }
}
