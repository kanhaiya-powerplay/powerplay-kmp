package com.example.core.dataService.models

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class DataTypeInfo(
    val type: KClass<*>,
    val kotlinType: KType? = null
)

inline fun <reified T> dataTypeInfoOf(): DataTypeInfo = DataTypeInfo(T::class, typeOfOrNull<T>())

inline fun <reified T> typeOfOrNull(): KType? = try {
    typeOf<T>()
} catch (_: Throwable) {
    null
}

