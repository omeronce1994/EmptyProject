package omeronce.android.emptyproject.scannovate.camera.repository

import androidx.lifecycle.LiveData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import omeronce.android.emptyproject.model.Result

interface RequestRepository {
    suspend fun getJson(flowId: String, byteArray: ByteArray): Result<String>
    fun observeJson(): LiveData<Result<String>>
}