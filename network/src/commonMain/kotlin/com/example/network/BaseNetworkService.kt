package com.example.network

import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.AppError
import com.example.core.dataService.models.DataTypeInfo
import com.example.network.interceptor.RequestInterceptorExecutor
import com.example.network.interceptor.ResponseInterceptorExecutor
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.responseInterceptor.ResponseInterceptor
import com.example.network.logging.ApiCallLog
import com.example.network.logging.NetworkLoggers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.contentLength
import io.ktor.util.date.getTimeMillis
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

open class BaseNetworkService : BaseDataService {

    open val responseInterceptors: List<ResponseInterceptor>
        get() = emptyList()
    open val requestInterceptors: List<RequestInterceptor>
        get() = emptyList()

    override suspend fun <T> perform(request: APIRequest, responseType: DataTypeInfo): APIResponse<T> {
        var request = request

        RequestInterceptorExecutor(requestInterceptors).execute<T>(request, responseType) {
            request = it
        }?.let { return it }

        var response = performHttpDefault<T>(request, responseType)

        ResponseInterceptorExecutor(responseInterceptors).execute<T>(request, responseType, response) {
            response = it
        }?.let { return it }

        return response
    }

    suspend fun <T> performHttpDefault(request: APIRequest, responseType: DataTypeInfo): APIResponse<T> {
        val startTs = getTimeMillis()
        return try {
            val httpResponse = HttpServiceFactory.execute(APIClientProvider.client, request)
            val bodyText = httpResponse.bodyAsText()
            val parsed = APIResponseHandler.handle<T>(bodyText, httpResponse.status.value, responseType)
            dispatchLog(request, httpResponse, bodyText, startTs)
            parsed
        } catch (e: Exception) {
            NetworkLoggers.dispatch(
                ApiCallLog(
                    url = request.urlString,
                    method = request.method.name,
                    statusCode = 0,
                    statusMessage = "",
                    requestHeaders = emptyMap(),
                    requestBody = serializeRequestBody(request),
                    responseHeaders = emptyMap(),
                    responseBody = null,
                    timestamp = startTs,
                    durationMs = getTimeMillis() - startTs,
                    contentLength = 0L,
                    errorMessage = e.message,
                )
            )
            APIResponse.Failure(AppError(message = e.message, cause = e.cause))
        }
    }

    private fun dispatchLog(
        request: APIRequest,
        httpResponse: HttpResponse,
        bodyText: String,
        startTs: Long,
    ) {
        NetworkLoggers.dispatch(
            ApiCallLog(
                url = request.urlString,
                method = request.method.name,
                statusCode = httpResponse.status.value,
                statusMessage = httpResponse.status.description,
                requestHeaders = httpResponse.request.headers.entries()
                    .associate { it.key to it.value.joinToString(", ") },
                requestBody = serializeRequestBody(request),
                responseHeaders = httpResponse.headers.entries()
                    .associate { it.key to it.value.joinToString(", ") },
                responseBody = bodyText,
                timestamp = startTs,
                durationMs = getTimeMillis() - startTs,
                contentLength = httpResponse.contentLength()
                    ?: httpResponse.headers[HttpHeaders.ContentLength]?.toLongOrNull()
                    ?: bodyText.length.toLong(),
                errorMessage = null,
            )
        )
    }

    private fun serializeRequestBody(request: APIRequest): String? {
        val wrapper = request.body ?: return null
        val payload = wrapper.body ?: return null
        val kotlinType = wrapper.bodyType?.kotlinType ?: return payload.toString()
        return runCatching {
            @Suppress("UNCHECKED_CAST")
            val ser = requestLogJson.serializersModule.serializer(kotlinType) as KSerializer<Any>
            requestLogJson.encodeToString(ser, payload)
        }.getOrElse { payload.toString() }
    }

    companion object {
        private val requestLogJson: Json = Json { encodeDefaults = true }
    }
}
