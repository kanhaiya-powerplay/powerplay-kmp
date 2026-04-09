package com.example.network.repository

import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIMethod
import com.example.core.dataService.models.APIResponse

import com.example.core.dataService.models.setBody
import com.example.network.NetworkAPIRoutes
import com.example.network.BaseAPIRepository
import com.example.network.DefaultNetworkService
import com.example.network.perform
import com.example.network.repository.models.RefreshTokenResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RefreshTokenAPIRepository(override val service: BaseDataService = DefaultNetworkService, val postData: PostData): BaseAPIRepository<RefreshTokenResponseModel> {

    @Serializable
    data class PostData(

        @SerialName("refresh_token")
        val refreshToken: String
    )

    override suspend fun execute(): APIResponse<RefreshTokenResponseModel> = service.perform(
        APIMethod.GET, NetworkAPIRoutes.RefreshToken){
        setBody(postData)
    }
}