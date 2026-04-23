package com.example.network

import com.example.core.dataService.models.APIMethod
import com.example.core.dataService.models.APIRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Url
import io.ktor.util.reflect.TypeInfo

object HttpServiceFactory {

    suspend fun execute(client: HttpClient, request: APIRequest): HttpResponse {
        return getService(client, request) {
            request.parameters.forEach {
                parameter(it.key, it.value)
            }
            request.body?.let { wrapper ->
                val payload = wrapper.body ?: return@let

                wrapper.bodyType?.let { dti ->
                    setBody(payload, TypeInfo(dti.type, dti.kotlinType))
                } ?: setBody(payload)
            }
        }
    }

    private suspend fun getService(
        client: HttpClient,
        request: APIRequest,
        block: HttpRequestBuilder.() -> Unit
    ): HttpResponse = when (request.method) {
        APIMethod.GET ->
            client.get(request.url, block)

        APIMethod.PUT ->
            client.put(request.url, block)

        APIMethod.POST ->
            client.post(request.url, block)

        APIMethod.DELETE ->
            client.delete(request.url, block)
    }
}

private val APIRequest.url: Url
    get() = Url(urlString)
