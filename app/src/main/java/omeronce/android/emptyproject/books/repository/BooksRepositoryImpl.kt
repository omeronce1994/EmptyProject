package omeronce.android.emptyproject.books.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.books.datasource.LocalBooksDataSource
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book

class BooksRepositoryImpl(private val remoteDataSource: BooksDataSource, private val localDataSource: BooksDataSource): BooksRepository {

    override suspend fun getBooks(): Result<List<Book>> {
        var result = remoteDataSource.getBooks()
        if(result is Result.Success) {
            result = localDataSource.insertBooks(result.value)
        }
        return result
    }

    override fun observeBooks(): LiveData<Result<List<Book>>> = localDataSource.observerBooks()
}