package omeronce.android.emptyproject.books.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.switchMap
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

    override suspend fun deleteAllBooks(): Result<Any> = withContext(dispatcher) {
        return@withContext try {
            booksDao.deleteTasks()
            Result.Success("success")
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun observerBooks(): Flow<Result<List<Book>>> = booksDao.observeBooks().map { Result.Success(it) }
}