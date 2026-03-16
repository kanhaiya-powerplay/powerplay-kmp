package com.example.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Project(

    val name: String?,

    @SerialName("_id")
    val id: String?
)
