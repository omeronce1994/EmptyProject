package omeronce.android.emptyproject.model.books


import com.google.gson.annotations.SerializedName

data class PlaceholderColor(
    @SerializedName("blue")
    val blue: Int = 0,
    @SerializedName("green")
    val green: Int = 0,
    @SerializedName("red")
    val red: Int = 0
)