package omeronce.android.emptyproject.di.module

import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.books.datasource.LocalBooksDataSource
import omeronce.android.emptyproject.books.datasource.RemoteBookDataSource
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.books.repository.BooksRepositoryImpl
import omeronce.android.emptyproject.books.viewmodel.BooksViewModel
import omeronce.android.emptyproject.model.room.daos.BooksDao
import org.koin.dsl.module

val bookModule = module {
    factory<BooksRepository> { BooksRepositoryImpl(provideRemoteDataSource(), provideLocaleDataSource(get())) }
    factory { BooksViewModel(get()) }
}

fun provideRemoteDataSource() = RemoteBookDataSource()

fun provideLocaleDataSource(booksDao: BooksDao) = LocalBooksDataSource(booksDao)