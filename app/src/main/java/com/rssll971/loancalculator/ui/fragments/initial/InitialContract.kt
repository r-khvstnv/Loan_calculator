package com.rssll971.loancalculator.ui.fragments.initial

import com.rssll971.loancalculator.ui.base.BaseContract

interface InitialContract {
    interface Presenter: BaseContract.Presenter<InitialView>{
        fun creditTypeWasClicked(isAnnuity: Boolean)
        fun infoWasClicked()
        fun changeThemeMode(isChecked: Boolean)
    }
    interface InitialView: BaseContract.View{
        fun requestCreditCalculationFragment(isAnnuity: Boolean)
        fun showInfo()
    }
}