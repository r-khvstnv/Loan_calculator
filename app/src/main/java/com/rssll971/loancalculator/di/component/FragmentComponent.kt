package com.rssll971.loancalculator.di.component

import com.rssll971.loancalculator.di.module.FragmentModule
import com.rssll971.loancalculator.ui.fragments.CreditCalculationFragment
import com.rssll971.loancalculator.ui.fragments.InitialFragment
import dagger.Component

@Component(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(creditCalculationFragment: CreditCalculationFragment)
    fun inject(initialFragment: InitialFragment)
}