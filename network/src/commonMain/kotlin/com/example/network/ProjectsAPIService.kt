package com.example.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProjectsAPIService {

    suspend fun getProjects(): List<Project>{
        return HTTPClientProvider.client.get("https://staging.getpowerplay.in/api/v2/projects/user"){
            parameter("org_id", "ORGn3xi6i5gl78i")
            parameter("page", 0)
            parameter("limit", 20)
        }.body()
    }
}