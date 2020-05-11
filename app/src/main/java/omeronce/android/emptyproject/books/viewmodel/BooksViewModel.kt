package omeronce.android.emptyproject.books.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.view.base.BaseViewModel

class BooksViewModel(val booksRepository: BooksRepository): BaseViewModel() {

    val books = booksRepository.observeBooks()

    fun getBooks() {
        viewModelScope.launch {
            booksRepository.getBooks()
        }
    }
}