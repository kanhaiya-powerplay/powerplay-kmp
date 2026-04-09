package com.example.network.interceptor.responseInterceptor

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.core.dataService.models.dataTypeInfoOf
import com.example.network.interceptor.InterceptorPriority

interface ResponseInterceptor {
    suspend fun <T> intercept(request: APIRequest,  response: APIResponse<T>, responseType: DataTypeInfo): APIResponse<T>
    fun <T> shouldIntercept(request: APIRequest, response: APIResponse<T>): Boolean

    fun priority(): InterceptorPriority
}