package com.example.network.apiClientConfigurers.configurations

import com.example.network.apiClientConfigurers.APIClientConfigurer
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ContentNegotiationConfig : APIClientConfigurer {

    override fun apply(config: HttpClientConfig<*>) {
        config.install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
}
