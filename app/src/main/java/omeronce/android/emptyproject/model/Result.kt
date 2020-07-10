package omeronce.android.emptyproject.model

import java.lang.Exception

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val value: T): Result<T>()
    data class Loading(val message: String = "Loading..."): Result<Nothing>()
    data class Error(val exception: Exception): Result<Nothing>()
}