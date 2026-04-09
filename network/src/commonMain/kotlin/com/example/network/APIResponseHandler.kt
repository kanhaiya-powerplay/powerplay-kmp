package com.example.network

import com.example.core.dataService.models.APIError
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.AppError
import com.example.core.dataService.models.DataTypeInfo
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo


object APIResponseHandler {

    suspend inline fun <T> handle(response: HttpResponse, responseType: DataTypeInfo): APIResponse<T> {

        try {

            val body = response.body<T>(TypeInfo(type = responseType.type, kotlinType = responseType.kotlinType))

            if (response.status.value == 200)
                return if (body != null) APIResponse.Success(body)
                else APIResponse.Failure(NetworkError.InvalidJSON)
            else {
                val error = response.body<APIError>()
                return APIResponse.Failure(error)
            }

        } catch (e: Exception) {
            return APIResponse.Failure(AppError(message = e.message, cause = e.cause))
        }

    }

}