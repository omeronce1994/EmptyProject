package omeronce.android.emptyproject.model.books


import androidx.recyclerview.widget.DiffUtil
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