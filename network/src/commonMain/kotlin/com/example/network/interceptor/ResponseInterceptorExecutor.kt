package com.example.network.interceptor

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.network.NetworkError
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.requestInterceptor.RequestInterceptorResult
import com.example.network.interceptor.responseInterceptor.ResponseInterceptor


class ResponseInterceptorExecutor(val interceptors: List<ResponseInterceptor>) {

    suspend fun <T> execute(request: APIRequest, responseType: DataTypeInfo, response: APIResponse<T>, onResponseUpdate: (APIResponse<T>) -> Unit): APIResponse<T>?{

        var response = response

        for (responseInterceptor in interceptors){

            if (!responseInterceptor.shouldIntercept(request, response)) continue

            response = responseInterceptor.intercept(request, response, responseType)

            if (responseInterceptor.priority() == InterceptorPriority.CRITICAL) return response
        }

        return null
    }
}