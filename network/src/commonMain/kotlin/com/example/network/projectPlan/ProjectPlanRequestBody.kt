package com.example.network.projectPlan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectPlanRequestBody(

    val page: Int = 0,

    val limit: Int = 40,

    val packages: List<String> = emptyList(),

    val tags: List<String> = emptyList(),

    @SerialName("assigned_users")
    val assignedUsers: List<String> = emptyList(),

    @SerialName("plan_status")
    val planStatus: List<Int> = emptyList(),

    @SerialName("task_status")
    val taskStatus: List<Int> = emptyList(),

    @SerialName("end_date_end")
    val endDateEnd: Long? = null,

    @SerialName("end_date_start")
    val endDateStart: Long? = null,

    @SerialName("start_date_end")
    val startDateEnd: Long? = null,

    @SerialName("start_date_start")
    val startDateStart: Long? = null,

    val dFilter: DFilter = DFilter(),

    val collapsed: String = ""
)

@Serializable
data class DFilter(

    @SerialName("custom_fields")
    val customFields: List<String> = emptyList()
)
