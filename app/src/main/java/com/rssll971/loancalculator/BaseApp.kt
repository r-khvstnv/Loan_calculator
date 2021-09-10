package com.rssll971.loancalculator

import android.app.Application
import com.rssll971.loancalculator.di.component.ActivityComponent
import com.rssll971.loancalculator.di.component.AppComponent
import com.rssll971.loancalculator.di.component.DaggerActivityComponent
import com.rssll971.loancalculator.di.component.DaggerAppComponent
import com.rssll971.loancalculator.di.module.ActivityModule
import com.rssll971.loancalculator.di.module.AppModule

class BaseApp: Application() {
    lateinit var  injector: AppComponent private set


    companion object{
        lateinit var INSTANCE: BaseApp private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        injector = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        injector.inject(this)


    }
}