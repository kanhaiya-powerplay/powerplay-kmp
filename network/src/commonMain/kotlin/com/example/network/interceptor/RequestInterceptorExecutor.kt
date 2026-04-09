package com.example.network.interceptor

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.network.NetworkError
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.requestInterceptor.RequestInterceptorResult

class RequestInterceptorExecutor(val interceptors: List<RequestInterceptor>) {

    suspend fun <T> execute(request: APIRequest, responseType: DataTypeInfo, onRequestUpdate: (APIRequest) -> Unit): APIResponse<T>?{

        var request = request

        for (requestInterceptor in interceptors){

            if (!requestInterceptor.shouldIntercept(request, responseType)) continue

            when(val result = requestInterceptor.intercept<T>(request, responseType)){

                is RequestInterceptorResult.Modified -> {

                    if (requestInterceptor.priority() == InterceptorPriority.CRITICAL) {
                        return APIResponse.Failure(
                            NetworkError.Unknown)
                    }
                    request = result.request
                    onRequestUpdate(request)
                }
                is RequestInterceptorResult.Respond<T> -> {
                    if (requestInterceptor.priority() == InterceptorPriority.CRITICAL){
                        return result.response
                    }
                }
            }
        }

        return null
    }
}