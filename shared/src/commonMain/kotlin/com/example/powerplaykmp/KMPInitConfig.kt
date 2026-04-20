package com.example.powerplaykmp

import com.example.core.KMPDebugObserver

/**
 * KMPInitConfig — the configuration object passed to PowerplayKMP.init()
 * on platforms where the full init is done via the common API (e.g. iOS).
 *
 * On Android, use initKMPForAndroid() (in :network androidMain) instead,
 * because it accepts an OkHttpClient which is an Android-only type.
 *
 * FIELDS
 * ──────
 * @param debugObserver  Observer that receives analytics events, screen views,
 *                       and errors from KMP.  Pass null on release builds.
 * @param baseUrl        Override the base URL for all KMP network requests.
 *                       Leave empty ("") to use URLs as embedded in each service.
 */
data class KMPInitConfig(
    val debugObserver: KMPDebugObserver? = null,
    val baseUrl: String = ""
)
