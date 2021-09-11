package com.rssll971.loancalculator.ui.main

import androidx.fragment.app.Fragment

class MainPresenter: MainContract.Presenter {
    private var view: MainContract.View? = null
    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }
}