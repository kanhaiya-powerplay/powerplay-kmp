package com.example.network.apiClientConfigurers

import io.ktor.client.HttpClientConfig


interface APIClientConfigurer{
    fun apply(config: HttpClientConfig<*>)
}