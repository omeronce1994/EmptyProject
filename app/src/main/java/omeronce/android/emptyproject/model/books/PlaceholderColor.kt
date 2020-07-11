package omeronce.android.emptyproject.model.books


import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class PlaceholderColor(
    @ColumnInfo(name = "blue")
    @SerializedName("blue")
    val blue: Int = 0,
    @ColumnInfo(name = "green")
    @SerializedName("green")
    val green: Int = 0,
    @ColumnInfo(name = "red")
    @SerializedName("red")
    val red: Int = 0
)