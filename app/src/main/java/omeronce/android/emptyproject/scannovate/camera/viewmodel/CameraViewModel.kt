package omeronce.android.emptyproject.scannovate.camera.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import omeronce.android.emptyproject.Const
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.scannovate.camera.helper.AutoFitTextureView
import omeronce.android.emptyproject.scannovate.camera.helper.CameraFragmentHelper
import omeronce.android.emptyproject.scannovate.camera.repository.RequestRepository
import omeronce.android.emptyproject.view.base.BaseViewModel
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class CameraViewModel(private val requestRepository: RequestRepository): BaseViewModel() {

    private val isFirstTime = AtomicBoolean(true)
    private val _json by lazy { MutableLiveData<Result<String>>() }
    val json: LiveData<Result<String>> = _json
    private lateinit var cameraFragmentHelper: CameraFragmentHelper

    fun getJson(flowId : String = Const.FLOW_ID, byteArray: ByteArray) {
        viewModelScope.launch {
            if(isFirstTime.compareAndSet(true, false)) {
                val result = requestRepository.getJson(flowId, byteArray)
                _json.value = result
                _showLoading.postValue(false)
            }
        }
    }

    fun initCameraHelper(fragment: Fragment?, textureView: AutoFitTextureView?, file: File) {
        cameraFragmentHelper =
            CameraFragmentHelper(
                fragment,
                textureView,
                file
            )
        cameraFragmentHelper.onPictureCapturedListener = {
            getJson(byteArray = file.readBytes())
        }
    }

    fun captureImage() {
        _showLoading.postValue(true)
        cameraFragmentHelper.captureImage()
    }
}