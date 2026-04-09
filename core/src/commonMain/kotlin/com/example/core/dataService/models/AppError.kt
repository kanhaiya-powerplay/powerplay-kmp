package com.example.core.dataService.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
open class AppError(

    @Transient
    open val errorCode: Int? = null,
    @Transient
    open val message: String? = null,

    @Transient
    val cause: Throwable? = null
)