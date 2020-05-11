package omeronce.android.emptyproject.utils.ktxextensions

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import java.util.concurrent.Executor

/**
 * Extension function to DataSource.Factory to retrieve LiveDtata instance
 */
@SuppressLint("RestrictedApi")
fun <Key, Value> DataSource.Factory<Key, Value>.toLiveData(
    config: PagedList.Config,
    initialLoadKey: Key? = null,
    boundaryCallback: PagedList.BoundaryCallback<Value>? = null,
    fetchExecutor: Executor = ArchTaskExecutor.getIOThreadExecutor()
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, config)
        .setInitialLoadKey(initialLoadKey)
        .setBoundaryCallback(boundaryCallback)
        .setFetchExecutor(fetchExecutor)
        .build()
}

val PAGE_SIZE = 50

fun pagedListConfig() = PagedList.Config.Builder()
    .setInitialLoadSizeHint(PAGE_SIZE)
    .setPageSize(PAGE_SIZE)
    .setEnablePlaceholders(true)
    .build()