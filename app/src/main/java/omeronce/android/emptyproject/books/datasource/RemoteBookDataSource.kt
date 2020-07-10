package omeronce.android.emptyproject.books.datasource

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omeronce.android.emptyproject.MyApplication
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.BooksResult
import org.koin.core.KoinComponent
import org.koin.core.get
import java.lang.Exception

class RemoteBookDataSource(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): BooksDataSource, KoinComponent {

    override suspend fun getBooks(): Result<List<Book>> = withContext(dispatcher){
        val application = get<Context>()
        val file_name = "books.json"
        try {
            val json_string = application.assets.open(file_name).bufferedReader().use{
                it.readText()
            }
            val gson = Gson()
            val booksResult = gson.fromJson<BooksResult>(json_string, BooksResult::class.java)
            Result.Success(booksResult.data)
        }
        catch (exception: Exception) {
            exception.printStackTrace()
            Result.Error(exception)
        }
    }
}