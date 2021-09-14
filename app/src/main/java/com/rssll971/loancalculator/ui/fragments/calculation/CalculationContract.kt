package com.rssll971.loancalculator.ui.fragments.calculation

import com.rssll971.loancalculator.models.MonthCreditModel
import com.rssll971.loancalculator.ui.base.BaseContract

interface CalculationContract {
    interface Presenter: BaseContract.Presenter<CalculatorView>{
        fun getCreditType(isAnnuity: Boolean)
        fun validateCreditData(loanAmount: String,
                               interest: String,
                               period: String,
                               gracePeriod: String)
    }
    interface CalculatorView: BaseContract.View{
        fun showCreditType(isAnnuity: Boolean)
        fun showPoorDataErrorMessage()
        fun onEstimationCallback(monthCreditList: ArrayList<MonthCreditModel>,
                                 resultInfo: MonthCreditModel)
    }
}