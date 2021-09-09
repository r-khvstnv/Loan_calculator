package com.rssll971.loancalculator.ui.main

import androidx.fragment.app.Fragment

class MainPresenter: MainContract.Presenter {
    private lateinit var view: MainContract.View
    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
        TODO("Not yet implemented")
    }

    override fun replaceFragmentTo(fragment: Fragment) {
        TODO("Not yet implemented")
    }

    override fun replaceFragmentAndBackStack(fragment: Fragment) {
        TODO("Not yet implemented")
    }

    override fun onBackButtonPressed() {
        TODO("Not yet implemented")
    }

    override fun changeThemeMode() {
        TODO("Not yet implemented")
    }

    override fun changeLanguage() {
        TODO("Not yet implemented")
    }
}