package com.example.network.projectPlan

import com.example.network.BaseAPIRoute

sealed class ProjectPlanAPIRoutes(override val value: String) : BaseAPIRoute(value) {

    class GetProjectPlan(projectId: String) : ProjectPlanAPIRoutes("/v1/project-plan/$projectId")
}
