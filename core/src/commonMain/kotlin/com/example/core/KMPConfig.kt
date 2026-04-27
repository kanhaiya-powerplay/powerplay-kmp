package com.example.core

import kotlin.concurrent.Volatile

// Runtime config. Host app sets `baseUrl` once at startup.
object KMPConfig {
    @Volatile
    var baseUrl: String = ""
}
