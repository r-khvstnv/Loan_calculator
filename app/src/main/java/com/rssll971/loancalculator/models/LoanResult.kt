package com.rssll971.loancalculator.models

data class LoanResult(
    val isAnnuityType: Boolean = true,
    val amountInitial: Float = 0f,
    val amountFinal: Float = 0f,
    val period: Int = 0,
    val interestGross: Int = 0,
    val interestNet: Int = 0,
    val monthlyPayments: List<MonthlyPayment> = emptyList()
)