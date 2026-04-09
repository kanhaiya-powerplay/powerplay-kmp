package com.example.network.interceptor.requestInterceptor

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse

sealed class RequestInterceptorResult<out T> {

    data class Modified(val request: APIRequest): RequestInterceptorResult<Nothing>()

    data class Respond<T>(val response: APIResponse<T>): RequestInterceptorResult<T>()
}
