package omeronce.android.emptyproject.di

import omeronce.android.emptyproject.di.module.bookModule
import omeronce.android.emptyproject.di.module.dbModule
import omeronce.android.emptyproject.di.module.networkModule
import omeronce.android.emptyproject.di.module.prefsModule

val appModules = listOf(prefsModule, dbModule, networkModule, bookModule)