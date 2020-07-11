package omeronce.android.emptyproject.model.books


import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "books")
data class Book(
    @ColumnInfo(name = "body")
    @SerializedName("body")
    val body: String = "",
    @Embedded
    @SerializedName("placeholderColor")
    val placeholderColor: PlaceholderColor = PlaceholderColor(),
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String = "",
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String = "",
    @PrimaryKey
    @ColumnInfo(name = "entryid")
    val id: String = UUID.randomUUID().toString()
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}