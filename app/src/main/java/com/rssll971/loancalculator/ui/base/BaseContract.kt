package com.rssll971.loancalculator.ui.base

import androidx.fragment.app.Fragment

interface BaseContract {
    interface Presenter<in V>{
        fun attach(view: V)
        fun detach()
        fun replaceFragmentTo(fragment: Fragment)
        fun replaceFragmentAndBackStack(fragment: Fragment)
        fun onBackButtonPressed()
        fun changeThemeMode()
        fun changeLanguage()
    }
    interface View{

    }
}