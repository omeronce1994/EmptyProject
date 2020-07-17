package omeronce.android.emptyproject.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun <T> singleSourceOfTruth(sourceObserver: () -> LiveData<Result<T>>, remoteRequest: suspend () -> (Result<T>), localRequest: suspend (T) -> Unit,
                            dispatcher: CoroutineDispatcher = Dispatchers.IO) = liveData(dispatcher) {
    var result: Result<T> = Result.Loading()
    emit(result)
    val local = sourceObserver.invoke()
    emitSource(local)
    result = remoteRequest.invoke()
    if (result is Result.Success) {
        localRequest(result.value)
    } else {
        emit(result)
        emitSource(local)
    }
}