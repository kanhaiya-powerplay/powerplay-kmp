package com.example.core.models.projectPlan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskMetaData(

    @SerialName("statusText")
    val statusText: String? = null,

    @SerialName("statusClass")
    val statusClass: String? = null
)
