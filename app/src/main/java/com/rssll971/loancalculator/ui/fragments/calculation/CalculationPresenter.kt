package com.rssll971.loancalculator.ui.fragments.calculation

class CalculationPresenter: CalculationContract.Presenter {
    private var view: CalculationContract.CalculatorView? = null
    private var isAnnuity: Boolean = true
    override fun attach(view: CalculationContract.CalculatorView) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun getCreditType(isAnnuity: Boolean) {
        this.isAnnuity = isAnnuity
    }

}