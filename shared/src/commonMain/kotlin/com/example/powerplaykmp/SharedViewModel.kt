package com.example.powerplaykmp

import com.example.core.KMPConfig
import com.example.network.Project
import com.example.network.ProjectRepository
import com.example.network.ProjectsAPIService

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

    fun getStaticData(): String {
        return "hello"
    }
}