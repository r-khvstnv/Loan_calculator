package com.rssll971.loancalculator.di.module

import android.app.Application
import com.rssll971.loancalculator.BaseApp
import com.rssll971.loancalculator.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    @PerApplication
    fun providesApplication(): Application = BaseApp()
}