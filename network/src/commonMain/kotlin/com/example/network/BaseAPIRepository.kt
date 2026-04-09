package com.example.network

import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIResponse

interface BaseAPIRepository<T> {

    /**
     * Developer can use any other [BaseDataService] implementation, for example [MockService], [LocalDBService].
     * All the implementations must implement [BaseDataService] interface
     *
     * How to use? -
     * Take service as a dependency in your class
     */
    val service: BaseDataService

    suspend fun execute(): APIResponse<T>

}