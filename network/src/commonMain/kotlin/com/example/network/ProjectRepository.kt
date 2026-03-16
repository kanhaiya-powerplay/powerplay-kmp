package com.example.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProjectRepository(
    private val apiService: ProjectsAPIService
) {

    suspend fun fetchProjects(): List<Project>{
        return  apiService.getProjects()
    }


  
}