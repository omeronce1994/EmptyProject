package omeronce.android.emptyproject.books.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
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

@ExperimentalCoroutinesApi
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
            `when`(remoteDataSource.getBooks()).thenReturn(Result.Success(initialData))
            val result = booksRepository.getBooks()
            assert(result is Result.Success)
            assert((result as Result.Success).value == initialData)
        }
    }

    @Test
    fun saveBooks() {
        runBlocking {
            val channel = ConflatedBroadcastChannel<List<Book>>()
            val flow: Flow<Result<List<Book>>> = channel.asFlow().map { Result.Success(it) }
            `when`(remoteDataSource.observerBooks()).thenReturn(flow)
            whenever(localDataSource.insertBooks(any())).then {
                val list = it.arguments[0] as List<Book>
                channel.offer(list)
            }

            booksRepository.saveBooks(initialData)

            flow.collect {
                assert(it is Result.Success)
                assert((it as Result.Success).value == initialData)
            }
        }
    }
}