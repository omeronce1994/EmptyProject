package omeronce.android.emptyproject.books.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import omeronce.android.emptyproject.MainCoroutineRule
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.books.repository.FakeBooksRepository
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

class BooksViewModelTest {

    private lateinit var repository: BooksRepository
    private lateinit var viewModel: BooksViewModel
    private lateinit var initialData: List<Book>

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        initialData = listOf(
            Book("Harry Potter", PlaceholderColor(255, 100, 50), "Harry Potter"),
            Book("Harry Potter2", PlaceholderColor(255, 100, 50), "Harry Potter2"),
            Book("Harry Potter3", PlaceholderColor(255, 100, 50), "Harry Potter3")
        )
        repository = spy(FakeBooksRepository(initialData))
        viewModel = BooksViewModel(repository)
    }

    @Test
    fun noRequestWhenNoObservers() {
        runBlocking {
            verify(repository, never()).getBooks()
        }
    }

    @Test
    fun getBooks() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
         viewModel.books.observeForTesting {
             assert(viewModel.showLoading.getOrAwaitValue())
             verify(repository, times(1)).observeBooks()
             mainCoroutineRule.resumeDispatcher()
             val result = viewModel.books.getOrAwaitValue()
             assert(result is Result.Success)
             assert((result as Result.Success).value == initialData)
             assert(!viewModel.showLoading.getOrAwaitValue())
         }

    }
}