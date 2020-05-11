package omeronce.android.emptyproject.di.module

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val prefsModule = module {
    single { androidContext().getSharedPreferences("prefs", Context.MODE_PRIVATE) }
}