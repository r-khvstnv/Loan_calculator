package com.rssll971.loancalculator.models

/**This class is also used for total credit info*/
data class MonthCreditModel(val month: Int,
                            val mainMonthDebt: Float,
                            val interestInMonth: Float,
                            val overallAmountInMonth: Float,
                            val remainder: Float)