package com.example.network

import com.example.network.apiClientConfigurers.APIClientConfigurer
import com.example.network.apiClientConfigurers.configurations.ContentNegotiationConfig
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.DefaultHeadersConfig
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.AcceptLanguageHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.AppVersionHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.AuthorizationHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.ContentTypeHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.LanguageHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.PlatformHeader
import io.ktor.client.HttpClient

object APIClientProvider {

    val configs: List<APIClientConfigurer>
        get() = listOf(
            ContentNegotiationConfig(),
            DefaultHeadersConfig(
                listOf(
                    AcceptLanguageHeader(), AppVersionHeader(), AuthorizationHeader(),
                    ContentTypeHeader(), LanguageHeader(), PlatformHeader()
                )
            )
        )

    var client: HttpClient = HttpClient {
        configs.forEach { it.apply(this) }
    }
}
