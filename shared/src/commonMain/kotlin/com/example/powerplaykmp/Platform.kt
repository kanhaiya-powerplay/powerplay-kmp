package com.example.powerplaykmp

import com.example.network.Project
import com.example.network.ProjectRepository
import com.example.network.ProjectsAPIService

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

suspend fun getAPIData(): List<Project>{
    return ProjectRepository(ProjectsAPIService()).fetchProjects()
}

public fun getStaticData(): String{
    return "hello"
}