package omeronce.android.emptyproject.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import omeronce.android.emptyproject.model.books.Book

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    fun observeBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books")
    suspend fun getBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)

    @Query("DELETE FROM books")
    suspend fun deleteTasks()
}