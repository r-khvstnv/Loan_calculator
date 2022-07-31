package com.rssll971.loancalculator.models

data class Loan(
    val isAnnuityType: Boolean = true,
    val amount: Int = 0,
    val interest: Int = 0,
    val period: Int = 0,
    val gracePeriod: Int = 0,
    val initialFee: Int = 0,
    val isInitialFeeInPercent: Boolean = true
)