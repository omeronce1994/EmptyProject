package omeronce.android.emptyproject.di.module

import android.content.Context
import androidx.room.Room
import omeronce.android.emptyproject.model.room.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single {  buildDb(androidContext())  }
}

fun buildDb(context: Context) =
    Room.databaseBuilder(context, AppDB::class.java, "app-db")
        .fallbackToDestructiveMigration()
        .build()
