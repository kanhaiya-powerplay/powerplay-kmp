package com.example.core.models.projectPlan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class ProjectPlanTask(

    @SerialName("_id")
    val id: String,

    val name: String? = null,

    @SerialName("project_id")
    val projectId: String? = null,

    @SerialName("package_id")
    val packageId: String? = null,

    @SerialName("package_name")
    val packageName: String? = null,

    @SerialName("start_date")
    val startDate: Long = 0,

    @SerialName("end_date")
    val endDate: Long = 0,

    @SerialName("week_start_date")
    val weekStartDate: Long = 0,

    @SerialName("week_end_date")
    val weekEndDate: Long = 0,

    @SerialName("actual_duration")
    val actualDuration: Int = 1,

    val duration: Int = 1,

    @SerialName("total_duration")
    val totalDuration: Int = 1,

    val type: Int = 0,

    @SerialName("task_status")
    val taskStatus: Int = 0,

    @SerialName("task_plan_status")
    val taskPlanStatus: Int = 0,

    @SerialName("actual_start_date")
    val actualStartDate: Long = 0,

    @SerialName("actual_end_date")
    val actualEndDate: Long = 0,

    @SerialName("parent_id")
    val parentId: String? = null,

    @SerialName("parent_name")
    val parentName: String? = null,

    val ancestors: List<String> = emptyList(),

    val children: List<String> = emptyList(),

    @SerialName("percent_parent_task")
    val percentParentTask: Double = 0.0,

    @SerialName("percentage_work_done")
    val percentageWorkDone: Double = 0.0,

    @SerialName("weighted_progress")
    val weightedProgress: Double = 0.0,

    @SerialName("is_active")
    val isActive: Boolean = true,

    @SerialName("task_display_id")
    val taskDisplayId: String? = null,

    val tags: List<JsonElement> = emptyList(),

    @SerialName("creator_id")
    val creatorId: String? = null,

    @SerialName("creator_name")
    val creatorName: String? = null,

    val unit: String? = null,

    val quantity: Double = 0.0,

    @SerialName("quantity_work_done")
    val quantityWorkDone: Double = 0.0,

    @SerialName("total_price")
    val totalPrice: Double = 0.0,

    @SerialName("total_price_work_done")
    val totalPriceWorkDone: Double = 0.0,

    @SerialName("measure_in_percentage")
    val measureInPercentage: Boolean = true,

    @SerialName("update_dateMS")
    val updateDateMs: Long = 0,

    @SerialName("index_number")
    val indexNumber: Int = 0,

    val allChildrenCount: Int = 0,

    val status: String? = null,

    @SerialName("expected_end_date")
    val expectedEndDate: Long = 0,

    @SerialName("has_work_order")
    val hasWorkOrder: Boolean = false,

    @SerialName("has_asset_usage_logs")
    val hasAssetUsageLogs: Boolean = false,

    @SerialName("is_wo_created_task")
    val isWoCreatedTask: Boolean = false,

    @SerialName("has_excess_quantity")
    val hasExcessQuantity: Boolean = false,

    val files: List<JsonElement> = emptyList(),

    val baselines: List<JsonElement> = emptyList(),

    @SerialName("created_dateMS")
    val createdDateMs: Long = 0,

    val createdAt: String? = null,

    val updatedAt: String? = null,

    @SerialName("is_late")
    val isLate: Boolean = false,

    @SerialName("meta_data")
    val metaData: TaskMetaData? = null,

    @SerialName("plan_status")
    val planStatus: Int = 0,

    @SerialName("children_has_work_order")
    val childrenHasWorkOrder: Boolean = false,

    @SerialName("issues_count")
    val issuesCount: Int = 0,

    @SerialName("has_associated_issues")
    val hasAssociatedIssues: Boolean = false,

    @SerialName("action_allowed_on_task")
    val actionAllowedOnTask: TaskActionAllowed? = null,

    @SerialName("custom_fields")
    val customFields: JsonObject? = null,

    @SerialName("latest_comment")
    val latestComment: String? = null
)
