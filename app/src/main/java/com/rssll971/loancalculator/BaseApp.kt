package com.rssll971.loancalculator

import android.app.Application
import com.rssll971.loancalculator.di.component.AppComponent
import com.rssll971.loancalculator.di.component.DaggerAppComponent
import com.rssll971.loancalculator.di.module.AppModule

class BaseApp: Application() {
    lateinit var  injector: AppComponent private set

    companion object{
        private var INSTANCE: BaseApp? = null
        @JvmStatic
        fun get(): BaseApp = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        injector = DaggerAppComponent.builder().appModule(AppModule()).build()
        injector.inject(this)
    }
}