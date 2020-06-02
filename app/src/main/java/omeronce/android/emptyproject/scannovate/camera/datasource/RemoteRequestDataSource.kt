package omeronce.android.emptyproject.scannovate.camera.datasource

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import omeronce.android.emptyproject.model.network.ApiGateway
import omeronce.android.emptyproject.model.network.WebApi
import omeronce.android.emptyproject.model.network.camera.CameraRequest


class RemoteRequestDataSource(private val webApi: WebApi, private val apiGateway: ApiGateway): RequestDataSource {

    override suspend fun getJson(flowId: String, byteArray: ByteArray) =
        apiGateway.getStringResult { webApi.getJson(CameraRequest(flowId),toMultiPartFile(byteArray = byteArray)) }

    fun toMultiPartFile(
        name: String = "tempFile",
        byteArray: ByteArray
    ): MultipartBody.Part {
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
        return MultipartBody.Part.createFormData(
            name,
            null,  // filename, this is optional
            reqFile
        )
    }
}