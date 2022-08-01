package com.rssll971.loancalculator.models

/**This class is also used for total credit info*/
data class MonthlyPayment(
    val month: Int,
    val mainDebtAmount: Float,
    val interestAmount: Float,
    val totalAmount: Float,
    val remainder: Float
)