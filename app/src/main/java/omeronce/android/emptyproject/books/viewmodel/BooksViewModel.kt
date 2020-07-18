package omeronce.android.emptyproject.books.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.singleSourceOfTruth
import omeronce.android.emptyproject.model.singleSourceOfTruthFlow
import omeronce.android.emptyproject.view.base.BaseViewModel

class BooksViewModel(private val booksRepository: BooksRepository, dispatcher: CoroutineDispatcher = Dispatchers.IO): BaseViewModel() {

    val books = singleSourceOfTruthFlow(booksRepository::observeBooks, booksRepository::getBooks, booksRepository::saveBooks, dispatcher, viewModelScope.coroutineContext)

    init {
        deleteAllBooks()
        _showLoading.addSource(books) {
            Log.i("BooksViewModel", "showLoading source $it")
            println(it.toString())
            _showLoading.value = it is Result.Loading || (it is Result.Success && it.value.isNullOrEmpty())
        }
    }

    fun deleteAllBooks() {
        viewModelScope.launch { booksRepository.deleteAllBooks() }
    }
}