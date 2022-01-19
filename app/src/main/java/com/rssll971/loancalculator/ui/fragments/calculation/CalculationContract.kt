package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.Context
import com.rssll971.loancalculator.models.MonthCreditModel
import com.rssll971.loancalculator.ui.base.BaseContract

interface CalculationContract {
    interface Presenter: BaseContract.Presenter<CalculatorView>{
        fun getCreditType(isAnnuity: Boolean, context: Context)
        fun validateCreditData(loanAmount: String,
                               interest: String,
                               period: String,
                               gracePeriod: String)
    }
    interface CalculatorView: BaseContract.View{
        fun showCreditType(title: String)
        fun showPoorDataErrorMessage()
        fun onEstimationCallback(monthCreditList: ArrayList<MonthCreditModel>,
                                 resultData: MonthCreditModel)
        fun provideRecyclerViewHeight(height: Int)
    }
}