package omeronce.android.emptyproject.scannovate.camera.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.Const
import omeronce.android.emptyproject.scannovate.camera.repository.RequestRepository
import omeronce.android.emptyproject.view.base.BaseViewModel

class CameraViewModel(private val requestRepository: RequestRepository): BaseViewModel() {

    fun getJson(flowId : String = Const.FLOW_ID, byteArray: ByteArray) {
        viewModelScope.launch {
            val result = requestRepository.getJson(flowId, byteArray)
        }
    }
}