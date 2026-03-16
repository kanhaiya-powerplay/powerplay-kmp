package com.example.network.HTTPClientConfigurers

import io.ktor.client.HttpClientConfig


interface HTTPClientConfigurer{
    fun apply(config: HttpClientConfig<*>)
}