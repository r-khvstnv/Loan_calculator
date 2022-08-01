package com.rssll971.loancalculator.ui.fragments.calculation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoanCalculationViewModel: ViewModel() {
    private var _typedLoanAmount = MutableStateFlow("")
    val typedLoanAmount: StateFlow<String> get() = _typedLoanAmount
    private var _typedLoanInterest = MutableStateFlow("")
    val typedLoanInterest: StateFlow<String> get() = _typedLoanInterest
    private var _typedLoanPeriod = MutableStateFlow("")
    val typedLoanPeriod: StateFlow<String> get() = _typedLoanPeriod
    private var _typedLoanGracePeriod = MutableStateFlow("")
    val typedLoanGracePeriod: StateFlow<String> get() = _typedLoanGracePeriod
    private var _typedLoanInitialFee = MutableStateFlow("")
    val typedLoanInitialFee: StateFlow<String> get() = _typedLoanInitialFee

    init {
        Log.i("MyViewModel", "init called")
    }

    override fun onCleared() {
        Log.i("MyViewModel", "onCleared called")
        super.onCleared()
    }

    fun setTypedLoanAmount(value: String){
        _typedLoanAmount.value = value
    }
    fun setTypedLoanInterest(value: String){
        _typedLoanInterest.value = value
    }
    fun setTypedLoanPeriod(value: String){
        _typedLoanPeriod.value = value
    }
    fun setTypedLoanGracePeriod(value: String){
        _typedLoanGracePeriod.value = value
    }
    fun setTypedLoanInitialFee(value: String){
        _typedLoanInitialFee.value = value
    }
}