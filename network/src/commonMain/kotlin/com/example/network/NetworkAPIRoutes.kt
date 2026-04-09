package com.example.network

sealed class NetworkAPIRoutes(override val value: String): BaseAPIRoute(value){

     object RefreshToken: NetworkAPIRoutes(value = "/v1/auth/refresh-token")
}
