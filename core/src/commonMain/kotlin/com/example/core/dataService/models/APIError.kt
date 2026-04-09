package com.example.core.dataService.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonObject


@Serializable
data class APIError(

    @SerialName("status_code")
    override val errorCode: Int? = null,

    @SerialName("message")
    override val message: String?,

    @SerialName("error_list")
    val errorList: ArrayList<ErrorItem>? = null,

    @SerialName("meta_data")
    val metaData: JsonObject? = null

): AppError(errorCode, message)


