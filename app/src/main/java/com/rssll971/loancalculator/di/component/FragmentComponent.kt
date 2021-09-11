package com.rssll971.loancalculator.di.component

import com.rssll971.loancalculator.di.module.FragmentModule
import com.rssll971.loancalculator.ui.fragments.calculation.CreditCalculationFragment
import com.rssll971.loancalculator.ui.fragments.initial.InitialFragment
import dagger.Component

@Component(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(initialFragment: InitialFragment)
    fun inject(calculationFragment: CreditCalculationFragment)
}