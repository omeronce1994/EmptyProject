package omeronce.android.emptyproject

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

interface IKoinTestApplication {
    fun injectModules(modules: List<Module>)
    fun removeModules(modules: List<Module>)
}