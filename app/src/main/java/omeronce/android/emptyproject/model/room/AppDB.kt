package omeronce.android.emptyproject.model.room

import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO add entities @Database(entities = arrayOf(ImageModel::class), version = 1)
abstract class AppDB : RoomDatabase() {

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        clearAllTables()
    }
}