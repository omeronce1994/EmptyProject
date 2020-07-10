package omeronce.android.emptyproject.books.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.view.base.BaseViewModel

class BooksViewModel(private val booksRepository: BooksRepository): BaseViewModel() {


    val books: LiveData<Result<List<Book>>> by lazy { booksRepository.observeBooks().also { getBooks() } }

    fun getBooks() {
        _showLoading.value = true
        viewModelScope.launch {
            booksRepository.getBooks()
            _showLoading.value = false
        }
    }
}