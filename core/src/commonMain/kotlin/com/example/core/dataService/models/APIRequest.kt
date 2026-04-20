package com.example.core.dataService.models

import com.example.core.KMPConfig

data class APIRequest(

    val method: APIMethod,

    val path: String,

    var parameters: MutableMap<String, String> = mutableMapOf(),

    var body: Body? = null

) {
    /**
     * Base URL for this request.
     *
     * Reads from KMPConfig.baseUrl at the moment each request is built, so
     * environment changes made via the debug UI take effect immediately on the
     * next request — no need to recreate the Ktor client.
     *
     * Default: "" (empty) — services that embed the full URL in `path` continue
     * to work unchanged because `urlString` becomes "/$fullUrl" which Ktor
     * parses correctly as an absolute URL.
     *
     * When the debug UI sets a base URL (e.g. "https://dev.api.getpowerplay.in/api/"):
     *   path      = "v2/projects/user"
     *   baseURL   = "https://dev.api.getpowerplay.in/api/"
     *   urlString = "https://dev.api.getpowerplay.in/api//v2/projects/user"
     *             → services should use path-only strings, not full URLs, for this to
     *               work properly with a non-empty base URL.
     */
    val baseURL: String
        get() = KMPConfig.baseUrl

    val urlString: String
        get() = if (baseURL.isEmpty()) path else "${baseURL.trimEnd('/')}/${path.trimStart('/')}"

    data class Body(var body: Any?, var bodyType: DataTypeInfo?)
}


fun APIRequest.parameter(key: String, value: String){
    parameters[key] = value
}

inline fun <reified T> APIRequest.setBody(body: T){
    this.body = APIRequest.Body(body, dataTypeInfoOf<T>())
}