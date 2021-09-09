package com.rssll971.loancalculator.ui.main

import com.rssll971.loancalculator.ui.base.BaseContract

interface MainContract {
    interface View: BaseContract.View{}
    interface Presenter: BaseContract.Presenter<View>{}
}