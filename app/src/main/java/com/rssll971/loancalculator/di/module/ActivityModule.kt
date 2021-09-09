package com.rssll971.loancalculator.di.module

import android.app.Activity
import com.rssll971.loancalculator.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule(private var activity: Activity) {
    @Provides
    fun providesActivity(): Activity{
        return activity
    }
    @Provides
    fun providesPresenter(): MainPresenter = MainPresenter()
}