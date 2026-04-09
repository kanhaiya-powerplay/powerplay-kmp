package com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers

import com.example.database.KeyValueStorage
import com.example.database.StorageKeys
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.APIHeader


class AuthorizationHeader: APIHeader {

    override fun key(): String = "Authorization"

    override fun value(): String = "Bearer ${KeyValueStorage.get<String>(StorageKeys.AUTH_TOKEN)}"

}