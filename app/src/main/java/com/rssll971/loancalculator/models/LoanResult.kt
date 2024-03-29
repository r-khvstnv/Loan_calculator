/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.loancalculator.models

/**Complete loan data model*/
data class LoanResult(
    val isAnnuityType: Boolean = true,
    /**Loan amount*/
    val amountInitial: Float = 0f,
    /**Payout amount*/
    val amountFinal: Float = 0f,
    val period: Int = 0,
    /**Interest rate*/
    val interestGross: Int = 0,
    /**Percentage difference between taken and paid amounts*/
    val interestNet: Int = 0,
    /**Payment schedule*/
    val monthlyPayments: List<MonthlyPayment> = emptyList()
)