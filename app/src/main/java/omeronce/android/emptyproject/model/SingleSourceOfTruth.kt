package omeronce.android.emptyproject.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

fun <T> singleSourceOfTruthFlow(sourceObserver: () -> Flow<Result<T>>, remoteRequest: suspend () -> (Result<T>), localRequest: suspend (T) -> Unit,
                            dispatcher: CoroutineDispatcher = Dispatchers.IO, context: CoroutineContext = EmptyCoroutineContext) = flow {
        emit(remoteRequest())
    }
    .onStart {
        emit(Result.Loading())
    }
    .flatMapLatest {
        if(it is Result.Success) {
            localRequest(it.value)
            sourceObserver()
        }
        else {
            flow { emit(it) }
        }
    }
    .catch { emit(Result.Error(it)) }
    .asLiveData(dispatcher + context)
