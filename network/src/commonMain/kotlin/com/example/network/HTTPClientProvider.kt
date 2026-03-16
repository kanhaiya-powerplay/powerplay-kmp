package com.example.network

import com.example.network.HTTPClientConfigurers.ContentNegotiationConfig
import com.example.network.HTTPClientConfigurers.DefaultHeadersConfig
import com.example.network.HTTPClientConfigurers.HTTPClientConfigurer
import io.ktor.client.HttpClient

object HTTPClientProvider {

    val configs: List<HTTPClientConfigurer>
        get() = listOf(ContentNegotiationConfig(), DefaultHeadersConfig())

    var client:HttpClient = HttpClient{
        configs.forEach { it.apply(this) }
    }
}


