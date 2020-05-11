package omeronce.android.emptyproject.books.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.view.base.BaseViewModel

class BooksViewModel(private val booksRepository: BooksRepository): BaseViewModel() {

    init {
        getBooks()
    }

    val books = booksRepository.observeBooks()

    fun getBooks() {
        viewModelScope.launch {
            booksRepository.getBooks()
        }
    }
}