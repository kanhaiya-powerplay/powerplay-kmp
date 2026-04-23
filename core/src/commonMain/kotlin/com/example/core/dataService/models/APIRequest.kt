package com.example.core.dataService.models

import com.example.core.KMPConfig

data class APIRequest(
    val method: APIMethod,
    val path: String,
    var parameters: MutableMap<String, String> = mutableMapOf(),
    var body: Body? = null
) {
    val baseURL: String
        get() = KMPConfig.baseUrl

    val urlString: String
        get() = if (baseURL.isEmpty()) path else "${baseURL.trimEnd('/')}/${path.trimStart('/')}"

    data class Body(var body: Any?, var bodyType: DataTypeInfo?)
}

fun APIRequest.parameter(key: String, value: String) {
    parameters[key] = value
}

inline fun <reified T> APIRequest.setBody(body: T) {
    this.body = APIRequest.Body(body, dataTypeInfoOf<T>())
}
