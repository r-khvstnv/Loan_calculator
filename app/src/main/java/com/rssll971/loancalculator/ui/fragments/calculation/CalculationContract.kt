package com.rssll971.loancalculator.ui.fragments.calculation

import com.rssll971.loancalculator.ui.base.BaseContract

interface CalculationContract {
    interface Presenter: BaseContract.Presenter<CalculatorView>{
        fun getCreditType(isAnnuity: Boolean)
        fun validateFields()
    }
    interface CalculatorView: BaseContract.View{

    }
}