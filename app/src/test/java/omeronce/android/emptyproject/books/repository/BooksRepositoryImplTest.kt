package omeronce.android.emptyproject.books.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.getOrAwaitValue
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.PlaceholderColor
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.`when`


class BooksRepositoryImplTest {

    private lateinit var initialData: List<Book>
    private lateinit var remoteDataSource: BooksDataSource
    private lateinit var localDataSource: BooksDataSource
    private lateinit var booksRepository: BooksRepository

    @Before
    fun setup() {
        initialData = listOf(Book(
            "Harry Potter", PlaceholderColor(255, 10, 50)
        ))
        remoteDataSource = mock()
        localDataSource = mock()
        booksRepository = BooksRepositoryImpl(remoteDataSource, localDataSource)
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getBooks() {
        runBlocking {
            val liveData =  MutableLiveData<Result<List<Book>>>()
            `when`(localDataSource.observerBooks()).thenReturn(liveData)
            `when`(remoteDataSource.getBooks()).thenReturn(Result.Success(initialData))
            `when`(localDataSource.insertBooks(initialData)).thenReturn(Result.Success(initialData))
            liveData.value = booksRepository.getBooks()
            assertEquals(booksRepository.observeBooks().getOrAwaitValue {  }, Result.Success(initialData))
        }
    }
}