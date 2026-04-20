package com.example.core

import okhttp3.OkHttpClient

/**
 * KMPAndroidConfig — holds Android-specific configuration that cannot live in
 * commonMain because it references Android/OkHttp types.
 *
 * WHY SEPARATE FROM KMPConfig
 * ───────────────────────────
 * KMPConfig is in commonMain — it must compile on iOS too, so it cannot
 * reference OkHttpClient.  This object lives in androidMain and is therefore
 * only compiled into the Android artefact.
 *
 * HOW THE OKHTTP CLIENT IS USED
 * ──────────────────────────────
 * Ktor's Android engine (ktor-client-okhttp) can accept a pre-configured
 * OkHttpClient via `engine { preconfigured = ... }`.  When the host app
 * passes its OkHttpClient here (one that already has ApiLogsInterceptor
 * attached), every HTTP call made by KMP is automatically logged in the
 * host app's existing debug infrastructure — zero extra code needed.
 *
 * On release builds the host app passes null, and createHttpClient.android.kt
 * falls back to a default OkHttpClient created by Ktor.
 *
 * INITIALIZATION ORDER
 * ─────────────────────
 * Set by initKMPForAndroid() BEFORE APIClientProvider.reinitialize() is called,
 * so the Ktor client is always built with the correct OkHttpClient.
 */
object KMPAndroidConfig {

    /**
     * The OkHttpClient provided by the host app.
     *
     * On debug/staging/preprod builds the host app should pass an OkHttpClient
     * that already has ApiLogsInterceptor added, so KMP calls appear in the
     * debug activity's API request list.
     *
     * Null on release — Ktor creates its own default OkHttpClient.
     */
    @Volatile
    var okHttpClient: OkHttpClient? = null
}
