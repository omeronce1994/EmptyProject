package omeronce.android.emptyproject.books.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book

open class AndroidFakeBooksRepository(private val data: List<Book> = mutableListOf()): BooksRepository {

    private val books by lazy { MutableLiveData<Result<List<Book>>>() }

    override fun observeBooks(): LiveData<Result<List<Book>>> = books

    override suspend fun getBooks(): Result<List<Book>> {
        val result = Result.Success(data)
        books.value = result
        return result
    }
}