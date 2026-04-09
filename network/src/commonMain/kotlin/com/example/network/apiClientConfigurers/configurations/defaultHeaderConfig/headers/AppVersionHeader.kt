package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.core.getAppVersion
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader

class AppVersionHeader: APIHeader {

    override fun key(): String = "app-version"

    override fun value(): String = getAppVersion()

}
