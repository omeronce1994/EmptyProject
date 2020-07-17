package omeronce.android.emptyproject.view.base

import androidx.lifecycle.*
import omeronce.android.emptyproject.utils.SingleLiveEvent

open class BaseViewModel: ViewModel() {

    protected val _showLoading by lazy { MediatorLiveData<Boolean>() }
    val showLoading: LiveData<Boolean> = _showLoading.distinctUntilChanged()

    val snackBarEvent = SingleLiveEvent<String>()
}