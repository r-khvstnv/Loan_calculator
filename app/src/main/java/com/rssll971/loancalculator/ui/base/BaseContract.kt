package com.rssll971.loancalculator.ui.base

interface BaseContract {
    interface Presenter<in V>{
        fun attach(view: V)
        fun detach()
    }
    interface View{}
}