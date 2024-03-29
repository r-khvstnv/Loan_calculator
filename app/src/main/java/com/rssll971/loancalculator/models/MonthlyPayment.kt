/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.loancalculator.models

/**Monthly payment model*/
data class MonthlyPayment(
    val month: Int,
    /**Principal payment amount*/
    val mainDebtAmount: Float,
    /**Interest payment amount*/
    val interestAmount: Float,
    /**Total payment (principal + interest)*/
    val totalAmount: Float,
    /**Remaining payment amount*/
    val remainder: Float
)