package omeronce.android.emptyproject.di.module

import android.content.Context
import androidx.room.Room
import omeronce.android.emptyproject.model.room.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { provideBooksDao(get()) }
    single { buildDb(androidContext())  }
}

fun buildDb(context: Context, inMemory: Boolean = false) = if (inMemory)
    Room.inMemoryDatabaseBuilder(context, AppDB::class.java)
        .build()
    else
    Room.databaseBuilder(context, AppDB::class.java, "app-db")
        .fallbackToDestructiveMigration()
        .build()

fun provideBooksDao(appDB: AppDB) = appDB.booksDao()
