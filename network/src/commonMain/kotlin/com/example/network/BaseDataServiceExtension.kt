package com.example.network

import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIMethod
import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.perform


suspend inline fun <reified T> BaseDataService.perform(method: APIMethod, route: BaseAPIRoute, block: APIRequest.() -> Unit): APIResponse<T>{
    val request = APIRequest(method, route.value)
    request.block()
    return perform(request)
}