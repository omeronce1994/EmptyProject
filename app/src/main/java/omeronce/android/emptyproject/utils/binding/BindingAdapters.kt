package omeronce.android.emptyproject.utils.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.utils.files.FileUtils
import java.io.File
import java.lang.Exception

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUri: String?) {
    val context = view.context
    val file = File(imageUri)
    val uri = FileUtils.getDownloadFolderUri(context,file)
    //just load image to view (no need to consider ratio in our case)
    Picasso.get().load(uri).fit().into(view)
}

@BindingAdapter("imageBook")
fun bindBook(view: ImageView, book: Book) {
    val placeholder = ColorDrawable(Color.rgb(book.placeholderColor.red, book.placeholderColor.green, book.placeholderColor.blue))
    if(book.url.isEmpty()) {
        view.setImageDrawable(placeholder)
        return
    }
    //just load image to view (no need to consider ratio in our case)
    Picasso.get().load(book.url).placeholder(placeholder).fit().into(view, object : Callback {
        override fun onSuccess() {
            Log.i("BindingAdapters", "bindBook: success")
        }

        override fun onError(e: Exception?) {
            Log.i("BindingAdapters", "bindBook: failed, message- ${e?.message}")
        }
    })
}