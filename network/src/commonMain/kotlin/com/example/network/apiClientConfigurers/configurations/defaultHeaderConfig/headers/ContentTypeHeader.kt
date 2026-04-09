package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader

class ContentTypeHeader: APIHeader {

    override fun key() = "Content-type"

    override fun value() = "application/json"
}
