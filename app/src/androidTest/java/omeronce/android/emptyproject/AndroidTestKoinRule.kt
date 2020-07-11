package omeronce.android.emptyproject

import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.module.Module

abstract class AndroidTestKoinRule: TestWatcher() {

    companion object {
        fun create(application: IKoinTestApplication, vararg modules: Module) = object: AndroidTestKoinRule() {
            override fun getApplication(): IKoinTestApplication = application

            override fun getModules(): Array<out Module> = modules
        }
    }

    override fun starting(description: Description?) {
        getApplication().injectModules(getModules().asList())
    }

    override fun finished(description: Description?) {
        getApplication().removeModules(getModules().asList())
    }

    abstract fun getApplication(): IKoinTestApplication
    abstract fun getModules(): Array<out Module>
}