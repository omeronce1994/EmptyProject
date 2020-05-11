package omeronce.android.emptyproject.model.books


import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("body")
    val body: String = "",
    @SerializedName("placeholderColor")
    val placeholderColor: PlaceholderColor = PlaceholderColor(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = ""
)