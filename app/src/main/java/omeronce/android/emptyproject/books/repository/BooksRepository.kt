package omeronce.android.emptyproject.books.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book

interface BooksRepository {

    fun observeBooks(): LiveData<Result<List<Book>>>
    suspend fun getBooks(): Result<List<Book>>
    suspend fun saveBooks(books: List<Book>)
    suspend fun deleteAllBooks()
}