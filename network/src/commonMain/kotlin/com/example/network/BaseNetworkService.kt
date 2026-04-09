package com.example.network

import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.network.interceptor.RequestInterceptorExecutor
import com.example.network.interceptor.ResponseInterceptorExecutor
import com.example.network.interceptor.requestInterceptor.interceptors.NoInternetInterceptor
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.responseInterceptor.ResponseInterceptor
import com.example.network.interceptor.responseInterceptor.interceptors.TokenExpiryInterceptor

open class BaseNetworkService: BaseDataService {

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

        val httpResponse = HttpServiceFactory.execute(APIClientProvider.client, request)
        return APIResponseHandler.handle(httpResponse, responseType)
    }

}