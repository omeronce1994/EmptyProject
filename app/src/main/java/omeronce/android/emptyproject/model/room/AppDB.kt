package omeronce.android.emptyproject.model.room

import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.room.daos.BooksDao

@Database(entities = arrayOf(Book::class), version = 1, exportSchema = true)
abstract class AppDB : RoomDatabase() {

    abstract fun booksDao(): BooksDao

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        clearAllTables()
    }
}