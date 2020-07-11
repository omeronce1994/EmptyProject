package omeronce.android.emptyproject.books.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.room.daos.BooksDao
import java.lang.Exception

class LocalBooksDataSource(private val booksDao: BooksDao, private val dispatcher: CoroutineDispatcher = Dispatchers.IO): BooksDataSource {

    override suspend fun getBooks(): Result<List<Book>> = withContext(dispatcher) {
        return@withContext try {
            Result.Success(booksDao.getBooks())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insertBooks(books: List<Book>): Result<List<Book>> = withContext(dispatcher) {
        return@withContext try {
            booksDao.insertBooks(books)
            Result.Success(books)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun observerBooks(): LiveData<Result<List<Book>>> = Transformations.map(booksDao.observeBooks()) {
        Result.Success(it)
    }
}