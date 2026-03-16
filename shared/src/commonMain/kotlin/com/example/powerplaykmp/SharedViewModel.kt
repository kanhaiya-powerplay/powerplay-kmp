package com.example.powerplaykmp

import com.example.network.Project
import com.example.network.ProjectRepository
import com.example.network.ProjectsAPIService

class SharedViewModel {

    suspend fun getAPIData(): List<Project>{

        try {
            val project = ProjectRepository(ProjectsAPIService()).fetchProjects()
            return project
        }
        catch (t: Throwable){
            println(t.message)
        }
        return emptyList()
    }

    fun getStaticData(): String{
        return "hello"
    }
}