package omeronce.android.emptyproject.scannovate.camera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.Const
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.scannovate.camera.repository.RequestRepository
import omeronce.android.emptyproject.view.base.BaseViewModel
import java.util.concurrent.atomic.AtomicBoolean

class CameraViewModel(private val requestRepository: RequestRepository): BaseViewModel() {

    private val isFirstTime = AtomicBoolean(true)
    private val _json by lazy { MutableLiveData<Result<String>>() }
    val json: LiveData<Result<String>> = _json

    fun getJson(flowId : String = Const.FLOW_ID, byteArray: ByteArray) {
        viewModelScope.launch {
            if(isFirstTime.compareAndSet(true, false)) {
                val result = requestRepository.getJson(flowId, byteArray)
                _json.value = result
            }
        }
    }
}