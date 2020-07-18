package omeronce.android.emptyproject.books.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import omeronce.android.emptyproject.MainCoroutineRule
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.books.repository.FakeBooksRepository
import omeronce.android.emptyproject.captureValues
import omeronce.android.emptyproject.getOrAwaitValue
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.PlaceholderColor
import omeronce.android.emptyproject.observeForTesting
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.concurrent.Executors
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class BooksViewModelTest {

    private lateinit var repository: BooksRepository
    private lateinit var viewModel: BooksViewModel
    private lateinit var initialData: MutableList<Book>

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        initialData = mutableListOf(
            Book("Harry Potter", PlaceholderColor(255, 100, 50), "Harry Potter"),
            Book("Harry Potter2", PlaceholderColor(255, 100, 50), "Harry Potter2"),
            Book("Harry Potter3", PlaceholderColor(255, 100, 50), "Harry Potter3")
        )
        repository = mock()
        viewModel = BooksViewModel(repository, mainCoroutineRule.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    @Test
    fun noRequestWhenNoObservers() {
        runBlocking {
            verify(repository, never()).getBooks()
        }
    }

    @Test
    fun getBooks() = mainCoroutineRule.runBlockingTest {
        val channel = ConflatedBroadcastChannel<List<Book>>()
        val flow = channel.asFlow().map{
            Result.Success(it)
        }
        whenever(repository.getBooks()).thenReturn(Result.Success(initialData))
        whenever(repository.observeBooks()).thenReturn(flow)
        whenever(repository.saveBooks(initialData)).then {
            val list = it.arguments[0] as List<Book>
            channel.offer(list)
        }

        viewModel.books.observeForTesting {
            val result = viewModel.books.getOrAwaitValue()
            assert(result is Result.Success)
            assert((result as Result.Success).value == initialData)
        }
    }
}