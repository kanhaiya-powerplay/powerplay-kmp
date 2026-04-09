package com.example.network.interceptor.requestInterceptor.interceptors

import com.example.core.connectivity.NetworkMonitor
import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.network.NetworkError
import com.example.network.interceptor.InterceptorPriority
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.requestInterceptor.RequestInterceptorResult

class NoInternetInterceptor: RequestInterceptor {

    override suspend fun <T> intercept(
        request: APIRequest,
        responseType: DataTypeInfo
    ): RequestInterceptorResult<T> = RequestInterceptorResult.Respond(
        APIResponse.Failure(
            NetworkError.NoInternet))

    override fun shouldIntercept(request: APIRequest, responseType: DataTypeInfo): Boolean = NetworkMonitor.isOnline()

    override fun priority(): InterceptorPriority = InterceptorPriority.CRITICAL
}