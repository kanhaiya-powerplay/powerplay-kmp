package com.example.network.repository.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponseModel(

    @SerialName("auth_token")
    val authToken: String,

    @SerialName("refresh_token")
    val refreshToken: String
)
