package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig

import com.example.network.apiClientConfigurers.APIClientConfigurer
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header

class DefaultHeadersConfig(private val customHeaders: List<APIHeader>) : APIClientConfigurer {

    override fun apply(config: HttpClientConfig<*>) {

        config.defaultRequest {
            customHeaders.forEach {
                header(it.key(), it.value())
            }
        }
    }
}