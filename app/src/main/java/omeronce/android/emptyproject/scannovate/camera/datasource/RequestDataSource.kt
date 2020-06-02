package omeronce.android.emptyproject.scannovate.camera.datasource

import okhttp3.RequestBody
import okhttp3.ResponseBody
import omeronce.android.emptyproject.model.Result

interface RequestDataSource {
    suspend fun getJson(flowId: String, byteArray: ByteArray): Result<String>
}