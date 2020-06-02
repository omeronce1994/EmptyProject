package omeronce.android.emptyproject.scannovate.main

import omeronce.android.emptyproject.scannovate.camera.repository.RequestRepository
import omeronce.android.emptyproject.view.base.BaseViewModel

class MainViewModel(requestRepository: RequestRepository): BaseViewModel() {

    val json = requestRepository.observeJson()
}