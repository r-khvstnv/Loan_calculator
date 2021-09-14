package com.rssll971.loancalculator.models

data class CreditModel(val isAnnuityType: Boolean = true,
                       val amount: Int = 0,
                       val interest: Int = 0,
                       val period: Int = 0,
                       val gracePeriod: Int = 0) {}