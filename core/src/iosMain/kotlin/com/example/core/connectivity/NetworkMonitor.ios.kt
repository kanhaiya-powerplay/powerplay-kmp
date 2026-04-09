package com.example.core.connectivity

import platform.Network.*

actual object NetworkMonitor {
    var isOnline: Boolean = true

    actual fun isOnline(): Boolean {
        return isOnline
    }
}