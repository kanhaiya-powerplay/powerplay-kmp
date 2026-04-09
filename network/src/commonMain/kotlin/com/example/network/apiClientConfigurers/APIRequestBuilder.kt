package com.example.network.apiClientConfigurers

import com.example.core.dataService.models.APIMethod
import com.example.core.dataService.models.APIRequest

data class APIRequestBuilder(
    val method: APIMethod,

    val path: String,

    val block: APIRequestBuilder.() -> Unit
){
    var parameters: MutableMap<String, String> = mutableMapOf()

    var body: APIRequest.Body? = null

    fun build(): APIRequest{
        return APIRequest(method, path, parameters, body)
    }
}
