package omeronce.android.emptyproject.books.datasource

import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.BooksResult

interface BooksDataSource {
    suspend fun getBooks(): Result<List<Book>>
}