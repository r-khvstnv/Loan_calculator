package com.rssll971.loancalculator.di.component

import com.rssll971.loancalculator.BaseApp
import com.rssll971.loancalculator.di.module.AppModule
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: BaseApp)
}