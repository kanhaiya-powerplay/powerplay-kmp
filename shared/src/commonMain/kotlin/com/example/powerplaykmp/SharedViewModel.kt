package com.example.powerplaykmp

import com.example.core.KMPConfig
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.AppError
import com.example.core.models.projectPlan.ProjectPlanTask
import com.example.network.Project
import com.example.network.ProjectRepository
import com.example.network.ProjectsAPIService
import com.example.network.projectPlan.ProjectPlanAPIRepository
import com.example.network.projectPlan.ProjectPlanRequestBody

class SharedViewModel {

    /**
     * Fetches projects from the API.
     *
     * On failure, the caught Throwable is forwarded to KMPConfig.debugObserver
     * (if one is registered) so it appears in the host app's crash/error log.
     * On release builds the observer is null — the catch block silently returns
     * an empty list exactly as before.
     */
    suspend fun getAPIData(): List<Project> {
        return try {
            ProjectRepository(ProjectsAPIService()).fetchProjects()
        } catch (t: Throwable) {
            // Forward to debug observer (no-op on release — observer is null)
            KMPConfig.debugObserver?.onError(
                throwable = t,
                context = "SharedViewModel.getAPIData"
            )
            println(t.message)
            emptyList()
        }
    }

    /**
     * Fetches the project plan (list of tasks) for the given project.
     *
     * @param projectId  The project ID, e.g. "PRJgf71umkqzc1d"
     * @param page       Zero-based page index for pagination
     * @param limit      Number of tasks per page
     * @param requestBody Full request body for advanced filtering. When provided,
     *                    [page] and [limit] params are ignored in favor of the
     *                    values inside [requestBody].
     * @return [APIResponse.Success] with the task list, or [APIResponse.Failure]
     *         with error details.
     */
    suspend fun getProjectPlan(
        projectId: String,
        page: Int = 0,
        limit: Int = 40,
        requestBody: ProjectPlanRequestBody = ProjectPlanRequestBody(page = page, limit = limit)
    ): APIResponse<List<ProjectPlanTask>> {
        return try {
            ProjectPlanAPIRepository(
                projectId = projectId,
                requestBody = requestBody
            ).execute()
        } catch (t: Throwable) {
            KMPConfig.debugObserver?.onError(
                throwable = t,
                context = "SharedViewModel.getProjectPlan — project: $projectId"
            )
            APIResponse.Failure(AppError(message = t.message, cause = t))
        }
    }

    fun getStaticData(): String {
        return "hello"
    }
}