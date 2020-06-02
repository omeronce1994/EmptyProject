package omeronce.android.emptyproject.model.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import omeronce.android.emptyproject.model.network.camera.CameraRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

//TODO: build api
interface WebApi {

    @Multipart
    @PUT("api/inquiries/")
    fun getJson(@Body cameraRequest: CameraRequest, @Part filePart:  MultipartBody.Part): Response<ResponseBody>
}