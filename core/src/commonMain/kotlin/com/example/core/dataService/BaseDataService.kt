package com.example.core.dataService

import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.DataTypeInfo
import com.example.core.dataService.models.dataTypeInfoOf

interface BaseDataService {
    suspend fun <T> perform(request: APIRequest, responseType: DataTypeInfo): APIResponse<T>
}

suspend inline fun <reified T> BaseDataService.perform(request: APIRequest): APIResponse<T> = perform(request, dataTypeInfoOf<T>())