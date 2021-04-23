package com.saltserv.commandbus

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class CommandBusApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appInstance = this

        initDependencyGraph()

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
    }

    protected open fun initDependencyGraph() {
        startKoin {
            androidContext(this@CommandBusApplication)
            modules(
                application,
                network
            )
        }
    }

    companion object {
        lateinit var appInstance: CommandBusApplication
    }
}