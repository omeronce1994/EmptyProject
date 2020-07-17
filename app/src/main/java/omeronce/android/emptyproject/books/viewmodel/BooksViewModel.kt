package omeronce.android.emptyproject.books.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.singleSourceOfTruth
import omeronce.android.emptyproject.view.base.BaseViewModel

class BooksViewModel(private val booksRepository: BooksRepository, dispatcher: CoroutineDispatcher = Dispatchers.IO): BaseViewModel() {

    val books: LiveData<Result<List<Book>>> = singleSourceOfTruth(booksRepository::observeBooks, booksRepository::getBooks, booksRepository::saveBooks, dispatcher)

    init {
        _showLoading.addSource(books) {
            //Log.i("BooksViewModel", it.toString())
            println(it.toString())
            _showLoading.value = it is Result.Loading || (it is Result.Success && it.value.isNullOrEmpty())
        }
    }

    fun deleteAllBooks() {
        viewModelScope.launch { booksRepository.deleteAllBooks() }
    }
}