package omeronce.android.emptyproject.di

import omeronce.android.emptyproject.di.module.networkModule
import omeronce.android.emptyproject.di.module.prefsModule
import omeronce.android.emptyproject.di.module.scanovateModule

val appModules = listOf(prefsModule, networkModule, scanovateModule)