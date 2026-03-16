package com.example.network.HTTPClientConfigurers

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ContentNegotiationConfig : HTTPClientConfigurer{

    override fun apply(config: HttpClientConfig<*>) {
        config.install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
}
