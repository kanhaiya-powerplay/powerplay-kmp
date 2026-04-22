package com.example.core.models.projectPlan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskActionAllowed(

    @SerialName("is_deletable")
    val isDeletable: TaskPermission? = null
)

@Serializable
data class TaskPermission(
    val allowed: Boolean = false,
    val reason: List<String> = emptyList()
)
