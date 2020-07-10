package omeronce.android.emptyproject.books.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book

class BooksRepositoryImpl(private val dataSource: BooksDataSource): BooksRepository {

    private val books: MutableLiveData<Result<List<Book>>> by lazy { MutableLiveData<Result<List<Book>>>() }

    override suspend fun getBooks(): Result<List<Book>> {
        val result = dataSource.getBooks()
        books.postValue(result)
        return result
    }

    override fun observeBooks(): LiveData<Result<List<Book>>> = books
}