package com.example.core

import kotlin.concurrent.Volatile

/**
 * KMPConfig — the single shared-state object that configures the KMP library
 * at runtime, after it has been published and consumed by a host app.
 *
 * WHY THIS EXISTS
 * ───────────────
 * KMP is published as a compiled library.  There are things the library can't
 * know at compile time — the debug observer the host app wants to use, or the
 * base URL the tester has chosen via the debug UI.  This object holds those
 * values so every KMP module can read them without needing a reference to the
 * host app.
 *
 * WHO WRITES IT
 * ─────────────
 * Only `initKMPForAndroid()` (androidMain, in :network) writes to KMPConfig.
 * That function is called once, early in the host app's Application.onCreate().
 *
 * WHO READS IT
 * ─────────────
 * • APIRequest.baseURL          — picks up `baseUrl` for every HTTP call
 * • BaseNetworkService          — calls `debugObserver` on errors
 * • Any KMP service/repo        — may call `debugObserver` for analytics events
 *
 * THREAD SAFETY
 * ─────────────
 * Both properties are @Volatile.  They are written once during app init and
 * only read afterwards, so volatile is sufficient (no lock needed).
 */
object KMPConfig {

    /**
     * The observer registered by the host app.
     * Null on release builds (host app never sets it).
     */
    @Volatile
    var debugObserver: KMPDebugObserver? = null

    /**
     * The base URL to prepend to every APIRequest path.
     *
     * Default is empty string — services that pass a full URL as their `path`
     * continue to work unchanged.  When the debug UI switches the backend
     * environment, it writes here so the next request picks up the new value
     * without recreating the Ktor client.
     *
     * Persisted value is loaded from KeyValueStorage (StorageKeys.KMP_BASE_URL)
     * during initKMPForAndroid() so the chosen environment survives app restarts.
     */
    @Volatile
    var baseUrl: String = ""
}
