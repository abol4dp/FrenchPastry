package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext

sealed class Resource<out  T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
    data object Loading: Resource<Nothing>()

}