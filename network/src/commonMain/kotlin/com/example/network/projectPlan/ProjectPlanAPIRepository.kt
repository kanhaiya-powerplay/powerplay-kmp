package com.example.network.projectPlan

import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIMethod
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.setBody
import com.example.core.models.projectPlan.ProjectPlanTask
import com.example.network.BaseAPIRepository
import com.example.network.DefaultNetworkService
import com.example.network.perform

class ProjectPlanAPIRepository(
    override val service: BaseDataService = DefaultNetworkService,
    val projectId: String,
    val requestBody: ProjectPlanRequestBody = ProjectPlanRequestBody()
) : BaseAPIRepository<List<ProjectPlanTask>> {

    override suspend fun execute(): APIResponse<List<ProjectPlanTask>> =
        service.perform(APIMethod.POST, ProjectPlanAPIRoutes.GetProjectPlan(projectId)) {
            setBody(requestBody)
        }
}
