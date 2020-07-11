package omeronce.android.emptyproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class KoinTestApp: Application(), IKoinTestApplication {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinTestApp)
            modules(emptyList())
        }
    }

    override fun injectModules(modules: List<Module>) {
        loadKoinModules(modules)
    }

    override fun removeModules(modules: List<Module>) {
        unloadKoinModules(modules)
    }
}