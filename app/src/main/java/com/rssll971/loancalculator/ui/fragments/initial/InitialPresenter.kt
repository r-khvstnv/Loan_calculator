package com.rssll971.loancalculator.ui.fragments.initial

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.rssll971.loancalculator.ui.base.BaseContract

class InitialPresenter: InitialContract.Presenter {
    private var view: InitialContract.InitialView? = null
    override fun attach(view: InitialContract.InitialView) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }
    override fun creditTypeWasClicked(isAnnuity: Boolean) {
        view?.requestCreditCalculationFragment(isAnnuity = isAnnuity)
    }

    override fun infoWasClicked() {
        view?.showInfo()
    }

    override fun changeThemeMode(isChecked: Boolean) {
        if (isChecked)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}