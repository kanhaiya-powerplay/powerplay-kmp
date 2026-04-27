package com.example.powerplaykmp

import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.AppError
import com.example.core.models.projectPlan.ProjectPlanTask
import com.example.network.Project
import com.example.network.ProjectRepository
import com.example.network.ProjectsAPIService
import com.example.network.projectPlan.ProjectPlanAPIRepository
import com.example.network.projectPlan.ProjectPlanRequestBody

class SharedViewModel {

    suspend fun getAPIData(): List<Project> {
        return try {
            ProjectRepository(ProjectsAPIService()).fetchProjects()
        } catch (t: Throwable) {
            println(t.message)
            emptyList()
        }
    }

    suspend fun getProjectPlan(
        projectId: String,
        page: Int = 0,
        limit: Int = 40,
        requestBody: ProjectPlanRequestBody = ProjectPlanRequestBody(page = page, limit = limit)
    ): APIResponse<List<ProjectPlanTask>> = try {

        ProjectPlanAPIRepository(
            projectId = projectId,
            requestBody = requestBody
        ).execute()

    } catch (t: Throwable) {
        APIResponse.Failure(
            AppError(message = t.message, cause = t)
        )
    }

    fun getStaticData(): String = "hello"
}
