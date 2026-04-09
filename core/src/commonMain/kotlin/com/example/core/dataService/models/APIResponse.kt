package com.example.core.dataService.models

sealed class APIResponse<out T> {

    data class Success<T>(val data: T): APIResponse<T>()

    data class Failure(val apiError: AppError): APIResponse<Nothing>()
}