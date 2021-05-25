package com.rssll971.loancalculator

data class MonthCreditModel(val month: Int,
                            val mainMonthDebt: Float,
                            val interestInMonth: Float,
                            val generalAmountInMonth: Float,
                            val remainder: Float)