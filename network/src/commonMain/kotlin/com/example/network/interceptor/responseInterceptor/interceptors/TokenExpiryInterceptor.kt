package com.example.network.interceptor.responseInterceptor.interceptors

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.database.KeyValueStorage
import com.example.database.StorageKeys
import com.example.network.BaseNetworkService
import com.example.network.interceptor.InterceptorPriority
import com.example.network.interceptor.responseInterceptor.ResponseInterceptor
import com.example.network.repository.RefreshTokenAPIRepository

class TokenExpiryInterceptor(val networkService: BaseNetworkService): ResponseInterceptor {


    override suspend fun <T> intercept(
        request: APIRequest,
        response: APIResponse<T>,
        responseType: DataTypeInfo
    ): APIResponse<T> {

        val tokenResponse = RefreshTokenAPIRepository(
            postData = RefreshTokenAPIRepository.PostData(
                refreshToken = KeyValueStorage.get<String>(
                    StorageKeys.AUTH_TOKEN
                ) ?: ""
            )
        ).execute()

        when(tokenResponse){

            is APIResponse.Success -> {

                KeyValueStorage[StorageKeys.AUTH_TOKEN] = tokenResponse.data.authToken
                KeyValueStorage[StorageKeys.REFRESH_TOKEN] = tokenResponse.data.refreshToken

                return networkService.perform(request, responseType)
            }

            is APIResponse.Failure -> {
                //return response
                TODO("Not yet implemented for logout")
            }
        }
    }

    override fun <T> shouldIntercept(request: APIRequest, response: APIResponse<T>): Boolean{
        return when(response){
            is APIResponse.Failure -> {
                (response.apiError.errorCode == 401)
            }
            else -> false
        }
    }

    override fun priority(): InterceptorPriority = InterceptorPriority.CRITICAL

}