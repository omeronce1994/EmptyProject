package omeronce.android.emptyproject.books.datasource

import androidx.lifecycle.LiveData
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.BooksResult

interface BooksDataSource {
    suspend fun getBooks(): Result<List<Book>>
    suspend fun insertBooks(books: List<Book>): Result<List<Book>>
    fun observerBooks(): LiveData<Result<List<Book>>>
}