package com.example.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class ProjectsAPIService {

    suspend fun getProjects(): List<Project>{
        return APIClientProvider.client.post("https://staging.getpowerplay.in/api/v2/projects/user"){
            parameter("org_id", "ORGn3xi6i5gl78i")
            parameter("page", 0)
            parameter("limit", 20)
            setBody(Project(name = "", id = ""))
        }.body()
    }
}