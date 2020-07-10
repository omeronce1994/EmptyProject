package omeronce.android.emptyproject.books.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
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

    private lateinit var dataSource: BooksDataSource
    private lateinit var booksRepository: BooksRepository

    @Before
    fun setup() {
        dataSource = mock()
        booksRepository = BooksRepositoryImpl(dataSource)
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getBooks() {
        runBlocking {
            `when`(dataSource.getBooks()).thenReturn(Result.Success(listOf(Book(
                "Harry Potter", PlaceholderColor(255, 10, 50)
                )
            )))
            booksRepository.getBooks()
            assertEquals(booksRepository.observeBooks().getOrAwaitValue {  }, Result.Success(listOf(Book(
                "Harry Potter", PlaceholderColor(255, 10, 50)
            )
            )))
        }
    }
}