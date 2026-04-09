package com.example.core.dataService.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ErrorItem(
    @SerialName("item_id")
    val itemId: String? = null,

    @SerialName("index_no")
    val indexNo: Int? = null,

    @SerialName("message")
    val message: String? = null
)