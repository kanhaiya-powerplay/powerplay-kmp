package com.example.network

import com.example.core.KMPAndroidConfig
import com.example.core.KMPConfig
import com.example.core.KMPDebugObserver
import com.example.database.KeyValueStorage
import com.example.database.StorageKeys
import okhttp3.OkHttpClient

/**
 * initKMPForAndroid — the single entry point the Android host app calls
 * during Application.onCreate() to wire the KMP library into its debug
 * infrastructure.
 *
 * WHY THIS LIVES IN :network androidMain
 * ───────────────────────────────────────
 * • It needs OkHttpClient (Android/OkHttp type → can't be in commonMain)
 * • It needs KeyValueStorage (from :database, already a transitive dep of :network)
 * • It needs KMPAndroidConfig and KMPConfig (from :core)
 * • It calls APIClientProvider.reinitialize() (in :network commonMain)
 * All of these are already available in :network's androidMain — no new
 * module dependencies needed.
 *
 * WHAT IT DOES — STEP BY STEP
 * ────────────────────────────
 * 1. Stores the host app's OkHttpClient in KMPAndroidConfig
 *    → createHttpClient.android.kt reads this and passes it to Ktor as
 *      `preconfigured`, so ALL KMP HTTP calls go through the host app's
 *      OkHttpClient (which has ApiLogsInterceptor on debug builds).
 *
 * 2. Stores the debug observer in KMPConfig
 *    → BaseNetworkService calls observer.onError() on failed responses
 *    → Any KMP service/repo can call observer.onAnalyticsEvent() / onScreenView()
 *
 * 3. Reads the persisted base URL from KeyValueStorage
 *    → The debug UI (AppInfoFragment) writes KMP_BASE_URL when the tester
 *      switches environments.  We load it here so it survives app restarts.
 *    → Stored in KMPConfig.baseUrl which APIRequest.baseURL reads dynamically.
 *
 * 4. Calls APIClientProvider.reinitialize()
 *    → Rebuilds the Ktor HttpClient using the freshly set OkHttpClient.
 *      This ensures the injected client is used even if APIClientProvider
 *      was accidentally accessed before initKMPForAndroid() was called.
 *
 * CALL SITE (powerplay-fieldapp-android / PowerplayApp.kt)
 * ─────────────────────────────────────────────────────────
 *  // Debug / Staging / Preprod:
 *  initKMPForAndroid(
 *      debugObserver = KMPDebugBridge,
 *      okHttpClient  = OkHttpClient.Builder()
 *                          .addInterceptor(DebugTools.getDebugLogsInterceptor()!!)
 *                          .build()
 *  )
 *
 *  // Release — pass nothing, all params default to null:
 *  initKMPForAndroid()
 *
 * @param debugObserver  Receives analytics events, screen views, and errors from
 *                       KMP.  Pass null on release builds.
 * @param okHttpClient   Pre-configured OkHttpClient from the host app.
 *                       Pass null on release — Ktor uses its default client.
 */
fun initKMPForAndroid(
    debugObserver: KMPDebugObserver? = null,
    okHttpClient: OkHttpClient? = null
) {
    // Step 1 — Store OkHttpClient so createHttpClient.android.kt can use it
    KMPAndroidConfig.okHttpClient = okHttpClient

    // Step 2 — Store debug observer so BaseNetworkService and others can call it
    KMPConfig.debugObserver = debugObserver

    // Step 3 — Load persisted base URL (written by AppInfoFragment when the
    //           tester switches environment in the debug UI).
    //           Falls back to "" if never set — services that embed full URLs
    //           in their `path` continue to work unchanged.
    val persistedBaseUrl = KeyValueStorage.get<String>(StorageKeys.KMP_BASE_URL) ?: ""
    KMPConfig.baseUrl = persistedBaseUrl

    // Step 4 — Rebuild the Ktor HttpClient now that OkHttpClient is set.
    //           Any subsequent network call will use the correctly configured client.
    APIClientProvider.reinitialize()
}
