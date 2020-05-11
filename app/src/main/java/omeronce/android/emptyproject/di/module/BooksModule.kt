package omeronce.android.emptyproject.di.module

import omeronce.android.emptyproject.books.datasource.BooksDataSource
import omeronce.android.emptyproject.books.datasource.RemoteBookDataSource
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.books.repository.BooksRepositoryImpl
import omeronce.android.emptyproject.books.viewmodel.BooksViewModel
import org.koin.dsl.module

val bookModule = module {
    factory<BooksDataSource> { RemoteBookDataSource() }
    factory<BooksRepository> { BooksRepositoryImpl(get()) }
    factory { BooksViewModel(get()) }
}