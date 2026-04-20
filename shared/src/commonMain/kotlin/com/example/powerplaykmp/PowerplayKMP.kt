package com.example.powerplaykmp

import com.example.core.KMPConfig
import com.example.network.APIClientProvider

/**
 * PowerplayKMP — the top-level entry point of the KMP library.
 *
 * This is the public API surface that all platforms (Android, iOS) can use
 * to initialise the library.  For Android specifically, prefer calling
 * `initKMPForAndroid()` (in :network androidMain) which additionally
 * accepts an OkHttpClient for transparent API logging.
 *
 * TYPICAL FLOW
 * ────────────
 *   Android host app  →  initKMPForAndroid()   (handles everything, incl. OkHttp)
 *   iOS host app      →  PowerplayKMP.init()   (sets observer + baseUrl)
 *
 * WHAT init() DOES
 * ─────────────────
 * 1. Writes KMPConfig.debugObserver  → BaseNetworkService uses it to report errors
 * 2. Writes KMPConfig.baseUrl        → APIRequest.baseURL reads it per request
 * 3. Calls APIClientProvider.reinitialize() → rebuilds Ktor client with fresh state
 */
object PowerplayKMP {

    /**
     * Initialise the KMP library with platform-agnostic config.
     *
     * Safe to call multiple times — each call replaces the previous config and
     * recreates the Ktor HttpClient.  In practice, call this once during
     * Application / AppDelegate startup.
     *
     * @param config  See [KMPInitConfig] for field documentation.
     */
    fun init(config: KMPInitConfig = KMPInitConfig()) {
        KMPConfig.debugObserver = config.debugObserver
        KMPConfig.baseUrl = config.baseUrl
        APIClientProvider.reinitialize()
    }
}
