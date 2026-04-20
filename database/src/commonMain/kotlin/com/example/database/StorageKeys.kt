package com.example.database

enum class StorageKeys {
    AUTH_TOKEN,
    REFRESH_TOKEN,

    /**
     * The base URL chosen by the tester via the debug UI (AppInfoFragment).
     * Written by AppInfoFragment when the environment chip is tapped.
     * Read by initKMPForAndroid() on startup to restore the chosen environment
     * across app restarts, then stored in KMPConfig.baseUrl for per-request use.
     *
     * Value example: "https://staging.getpowerplay.in/api/"
     * Empty string means "use the URL embedded in each service (default)".
     */
    KMP_BASE_URL
}