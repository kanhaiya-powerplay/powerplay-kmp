package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader

class PlatformHeader: APIHeader {

    override fun key(): String = "x-platform"

    override fun value(): String = xPlatform()
}

expect fun xPlatform(): String