package omeronce.android.emptyproject.books.datasource

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import omeronce.android.emptyproject.MyApplication
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.BooksResult
import org.koin.core.KoinComponent
import org.koin.core.get
import java.lang.Exception

class RemoteBookDataSource(): BooksDataSource, KoinComponent {
    override suspend fun getBooks(): Result<List<Book>> {
        val application = get<Context>()
        val file_name = "books.json"
        try {
            val json_string = application.assets.open(file_name).bufferedReader().use{
                it.readText()
            }
            val gson = Gson()
            val booksResult = gson.fromJson<BooksResult>(json_string, BooksResult::class.java)
            return Result.Success(booksResult.data)
        }
        catch (exception: Exception) {
            exception.printStackTrace()
            return Result.Error(exception)
        }
    }
}