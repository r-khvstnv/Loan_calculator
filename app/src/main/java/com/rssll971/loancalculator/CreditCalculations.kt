package com.rssll971.loancalculator

class CreditCalculations(private var month: Int,
                         private var mainMonthDebt: Long,
                         private var percentPerMonth: Long,
                         private var remainder: Long){
    fun getMonth(): Int{
        return month
    }
    fun getMainMonthDebt(): Long{
        return mainMonthDebt
    }
    fun getPercentPerMonth(): Long{
        return percentPerMonth
    }
    fun getRemainder(): Long{
        return remainder
    }
}