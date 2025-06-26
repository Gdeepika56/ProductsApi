package com.example.cleancodeproducts.data.repository

sealed class ApiState<out T> {
    data object Loading: ApiState<Nothing>()
    data class Success<out T>(val data: T): ApiState<T>()
    data class Error(val error:String): ApiState<Nothing>()
}