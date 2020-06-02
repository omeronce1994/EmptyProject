package omeronce.android.emptyproject.scannovate.camera.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.scannovate.camera.datasource.RequestDataSource

class RequestRepositoryImpl(private val dataSource: RequestDataSource): RequestRepository {

    private val jsonResponse by lazy { MutableLiveData<Result<String>>() }

    override fun observeJson(): LiveData<Result<String>> = jsonResponse

    override suspend fun getJson(flowId: String, byteArray: ByteArray): Result<String> {
        val result = dataSource.getJson(flowId, byteArray)
        jsonResponse.postValue(result)
        return result
    }
}