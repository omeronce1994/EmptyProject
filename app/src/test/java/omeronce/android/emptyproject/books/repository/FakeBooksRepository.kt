package omeronce.android.emptyproject.books.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book

open class FakeBooksRepository(private val data: MutableList<Book> = mutableListOf()): BooksRepository {

    private val books by lazy { MutableLiveData<Result<List<Book>>>() }

    override fun observeBooks(): LiveData<Result<List<Book>>> = books

    override suspend fun getBooks(): Result<List<Book>> {
        val result = Result.Success(data)
        return result
    }

    override suspend fun saveBooks(books: List<Book>) {
        data.addAll(books)
        update()
    }

    override suspend fun deleteAllBooks() {
        data.clear()
        update()
    }

    private fun update() {
        books.value = Result.Success(data)
    }
}