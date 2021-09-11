package com.rssll971.loancalculator.di.module

import com.rssll971.loancalculator.ui.fragments.calculation.CalculationContract
import com.rssll971.loancalculator.ui.fragments.calculation.CalculationPresenter
import com.rssll971.loancalculator.ui.fragments.initial.InitialContract
import com.rssll971.loancalculator.ui.fragments.initial.InitialPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {
    @Provides
    fun providesInitialPresenter(): InitialContract.Presenter = InitialPresenter()
    @Provides
    fun providesCalculatorPresenter(): CalculationContract.Presenter = CalculationPresenter()
}