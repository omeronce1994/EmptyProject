package omeronce.android.emptyproject.di.module

import omeronce.android.emptyproject.scannovate.camera.datasource.RemoteRequestDataSource
import omeronce.android.emptyproject.scannovate.camera.datasource.RequestDataSource
import omeronce.android.emptyproject.scannovate.camera.repository.RequestRepository
import omeronce.android.emptyproject.scannovate.camera.repository.RequestRepositoryImpl
import omeronce.android.emptyproject.scannovate.camera.viewmodel.CameraViewModel
import omeronce.android.emptyproject.scannovate.main.MainViewModel
import org.koin.dsl.module

val scanovateModule = module {
    factory<RequestDataSource> { RemoteRequestDataSource(get(), get()) }
    single <RequestRepository> { RequestRepositoryImpl(get()) }
    factory { CameraViewModel(get()) }
    factory { MainViewModel(get()) }
}