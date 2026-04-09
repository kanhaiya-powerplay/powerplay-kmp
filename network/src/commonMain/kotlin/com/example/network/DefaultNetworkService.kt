package com.example.network


import com.example.network.interceptor.requestInterceptor.interceptors.NoInternetInterceptor
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.responseInterceptor.ResponseInterceptor
import com.example.network.interceptor.responseInterceptor.interceptors.TokenExpiryInterceptor

object DefaultNetworkService: BaseNetworkService() {

    override val requestInterceptors: List<RequestInterceptor>
        get() = listOf(NoInternetInterceptor())

    override val responseInterceptors: List<ResponseInterceptor>
        get() = listOf(TokenExpiryInterceptor(this))
}
