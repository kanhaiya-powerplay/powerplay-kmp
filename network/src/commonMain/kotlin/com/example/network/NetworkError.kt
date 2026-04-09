package com.example.network

import com.example.core.dataService.models.AppError

sealed class NetworkError {

    object NoInternet: AppError(-1,  "You’re offline. Please connect to the internet and try again.")

    object InvalidJSON: AppError(1001,  "Something went wrong. JSON parsing failed")

    object Unknown: AppError(-99,  "Something went wrong. Please contact developer/support team")

}