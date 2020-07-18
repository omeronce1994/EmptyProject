package omeronce.android.emptyproject.books.datasource

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.BooksResult

interface BooksDataSource {
    suspend fun getBooks(): Result<List<Book>>
    suspend fun insertBooks(books: List<Book>): Result<List<Book>>
    suspend fun deleteAllBooks(): Result<Any>
    fun observerBooks(): Flow<Result<List<Book>>>
}