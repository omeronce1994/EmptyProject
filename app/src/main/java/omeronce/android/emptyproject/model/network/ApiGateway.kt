package omeronce.android.emptyproject.model.network

import okhttp3.ResponseBody
import omeronce.android.emptyproject.model.Result
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class ApiGateway {

    suspend fun<T : Any> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    return Result.Success(body)
                }
            }
            return Result.Error(HttpException(response))
        }
        catch (exception: Exception) {
            return Result.Error(exception)
        }
    }

    suspend fun getStringResult(call: suspend () -> Response<ResponseBody>): Result<String> {
        try {
            val response = call()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    return Result.Success(body.string())
                }
            }
            return Result.Error(HttpException(response))
        }
        catch (exception: Exception) {
            return Result.Error(exception)
        }
    }

    private fun <T> error(message: String) = Result.Error(Exception(message))
}