package omeronce.android.emptyproject.model.network.camera

import com.google.gson.annotations.SerializedName

data class CameraRequest(
    @SerializedName("flowId")
    val flowId: String
)