package com.rssll971.loancalculator.ui.fragments.initial

import com.rssll971.loancalculator.ui.base.BaseContract

interface InitialContract {
    interface Presenter: BaseContract.Presenter<InitialView>{
        /**Method receive chosen type and prepare next fragment*/
        fun creditTypeWasClicked(isAnnuity: Boolean)
        fun infoWasClicked()
        /**Handle UI events corresponding to Theme mode*/
        fun switchNightMode(isChecked: Boolean)
    }
    interface InitialView: BaseContract.View{
        /**Request fragment when user can estimate loan*/
        fun requestCreditCalculationFragment(isAnnuity: Boolean)
        /**Method show info about app*/
        fun showInfo()
    }
}