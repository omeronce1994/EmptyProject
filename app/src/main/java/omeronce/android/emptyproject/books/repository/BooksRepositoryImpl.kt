package omeronce.android.emptyproject.books.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.books.datasource.LocalBooksDataSource
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book

class BooksRepositoryImpl(private val remoteDataSource: BooksDataSource, private val localDataSource: BooksDataSource): BooksRepository {

    override suspend fun getBooks(): Result<List<Book>> {
        var result = remoteDataSource.getBooks()
        return result
    }

    override fun observeBooks(): LiveData<Result<List<Book>>> = localDataSource.observerBooks()

    override suspend fun saveBooks(books: List<Book>) {
        localDataSource.insertBooks(books)
    }

    override suspend fun deleteAllBooks() {
        localDataSource.deleteAllBooks()
        Log.i("BooksRepositoryImpl", "deleted all books")
    }
}