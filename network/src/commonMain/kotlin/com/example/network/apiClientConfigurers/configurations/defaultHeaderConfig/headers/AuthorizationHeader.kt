package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.database.KeyValueStorage
import com.example.database.StorageKeys
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader

class AuthorizationHeader : APIHeader {

    override fun key(): String = "Authorization"

    override fun value(): String {
        val token = KeyValueStorage.get<String>(StorageKeys.AUTH_TOKEN)
        return if (token.isNullOrEmpty()) "" else "Bearer $token"
    }
}
