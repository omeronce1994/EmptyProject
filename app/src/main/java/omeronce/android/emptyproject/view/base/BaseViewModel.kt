package omeronce.android.emptyproject.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import omeronce.android.emptyproject.utils.SingleLiveEvent

class BaseViewModel: ViewModel() {

    protected val _showLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val showLoading: LiveData<Boolean> = _showLoading

    val snackBarEvent = SingleLiveEvent<String>()
}