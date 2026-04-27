package com.example.network

import com.example.core.dataService.models.APIError
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.AppError
import com.example.core.dataService.models.DataTypeInfo
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

internal val responseJson: Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

object APIResponseHandler {

    fun <T> handle(
        bodyText: String,
        statusCode: Int,
        responseType: DataTypeInfo,
    ): APIResponse<T> {
        return try {
            if (statusCode in 200..299) {
                val kotlinType = responseType.kotlinType
                    ?: return APIResponse.Failure(NetworkError.InvalidJSON)
                @Suppress("UNCHECKED_CAST")
                val body = responseJson.decodeFromString(
                    responseJson.serializersModule.serializer(kotlinType) as KSerializer<T>,
                    bodyText,
                )
                if (body != null) APIResponse.Success(body)
                else APIResponse.Failure(NetworkError.InvalidJSON)
            } else {
                val error = responseJson.decodeFromString(APIError.serializer(), bodyText)
                APIResponse.Failure(error)
            }
        } catch (e: Exception) {
            APIResponse.Failure(AppError(message = e.message, cause = e.cause))
        }
    }
}
