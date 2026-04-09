package com.example.network.interceptor.requestInterceptor

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.core.dataService.models.dataTypeInfoOf
import com.example.network.interceptor.InterceptorPriority

interface RequestInterceptor {
    suspend fun <T> intercept(request: APIRequest, responseType: DataTypeInfo): RequestInterceptorResult<T>
    fun shouldIntercept(request: APIRequest, responseType: DataTypeInfo): Boolean
    fun priority(): InterceptorPriority
}