package com.rssll971.loancalculator

class MonthCreditInfo(private var month: Int,
                      private var mainMonthDebt: Float,
                      private var InterestInMonth: Float,
                      private var generalAmountInMonth: Float,
                      private var remainder: Float){
    fun getMonth(): Int{
        return month
    }
    fun getMainMonthDebt(): Float{
        return mainMonthDebt
    }
    fun getInterestInMonth(): Float{
        return InterestInMonth
    }
    fun getGeneralAmountInMonth(): Float{
        return generalAmountInMonth
    }
    fun getRemainder(): Float{
        return remainder
    }
}