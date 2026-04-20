package com.example.core

/**
 * KMPDebugObserver — the contract between the KMP library and any host app
 * that wants to observe KMP's internal events in a debug UI.
 *
 * HOW IT WORKS
 * ─────────────
 * The KMP library itself never stores logs or shows a UI.
 * Instead, it calls back through this interface whenever something
 * interesting happens (analytics event, screen view, error).
 *
 * The host app (e.g. powerplay-fieldapp-android) implements this interface
 * using whatever debug infrastructure it already has.  On release builds the
 * host app simply passes null — every call site is null-safe, so nothing runs.
 *
 * USAGE IN HOST APP
 * ─────────────────
 *  object KMPDebugBridge : KMPDebugObserver {
 *      override fun onAnalyticsEvent(...) { DebugTools.saveEvent(...) }
 *      override fun onScreenView(...)     { DebugTools.saveScreen(...) }
 *      override fun onError(...)          { DebugTools.logCrash(...) }
 *  }
 *
 *  // During app init (non-release only):
 *  initKMPForAndroid(debugObserver = KMPDebugBridge, okHttpClient = client)
 */
interface KMPDebugObserver {

    /**
     * Called whenever a KMP module fires a business analytics event.
     *
     * @param name       Event name, e.g. "project_fetched"
     * @param properties Key-value pairs describing the event
     */
    fun onAnalyticsEvent(name: String, properties: Map<String, Any>)

    /**
     * Called when a KMP module registers a screen/page view.
     *
     * @param name       Screen name, e.g. "ProjectListScreen"
     * @param properties Additional metadata about the screen
     */
    fun onScreenView(name: String, properties: Map<String, Any>)

    /**
     * Called when KMP catches an exception or receives an error API response.
     * The host app can forward this to its crash logger / debug DB.
     *
     * @param throwable The exception that was caught
     * @param context   Human-readable string identifying where in KMP it happened,
     *                  e.g. "BaseNetworkService.performHttpDefault - /v2/projects"
     */
    fun onError(throwable: Throwable, context: String)
}
