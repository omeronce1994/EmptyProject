package omeronce.android.emptyproject.model

import java.lang.Exception

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class Loading(val message: String = "Loading..."): Result<Nothing>()
    data class Error(val exception: Exception): Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success -> this.value.toString()
            is Loading -> this.message
            is Error -> this.exception.message ?: " Unknown Error" + ": "  + this.exception.printStackTrace()
        }
    }
}