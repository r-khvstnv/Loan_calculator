package com.rssll971.loancalculator.di.component

import com.rssll971.loancalculator.di.module.ActivityModule
import com.rssll971.loancalculator.ui.main.MainActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}