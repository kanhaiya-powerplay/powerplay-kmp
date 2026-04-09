package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader

class AcceptLanguageHeader: APIHeader {

    override fun key(): String = "accept-language"

    override fun value(): String = "en"
}