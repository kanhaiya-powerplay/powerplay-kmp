package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader

class LanguageHeader: APIHeader {

    override fun key(): String = "language"

    override fun value(): String = "en"
}