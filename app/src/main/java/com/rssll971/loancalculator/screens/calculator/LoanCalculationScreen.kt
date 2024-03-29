/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.loancalculator.screens.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.SharedViewModel
import com.rssll971.loancalculator.ui.composable.AdvertView
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme
import com.rssll971.loancalculator.utils.Constants

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun LoanCalculationContentPreview(){
    LoanCalcTheme {
        LoanCalculationContent(
            viewModel = viewModel(),
            onClosePressed = {},
            onPaymentSchedulePressed = {}
        )
    }
}

@Composable
fun LoanCalculationScreen(
    navController: NavController,
    isAnnuityLoanType: Boolean,
    viewModel: SharedViewModel
){
    viewModel.setLoanType(isAnnuityLoanType)

    Column {
        AdvertView()

        Spacer(modifier = Modifier.padding(10.dp))

        LoanCalculationContent(
            viewModel = viewModel,
            onClosePressed = { navController.popBackStack() },
            onPaymentSchedulePressed = { navController.navigate(Constants.DEST_DETAILS) }
        )
    }
}


@Composable
fun LoanCalculationContent(
    viewModel: SharedViewModel,
    onClosePressed: () -> Unit,
    onPaymentSchedulePressed: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        val isAnnuity by viewModel.isAnnuity.collectAsState()
        CalculatorTopBar(
            loanType =
            if (isAnnuity) stringResource(id = R.string.title_annuity)
            else stringResource(id = R.string.title_differential),
            onCloseClick = onClosePressed,
            onSwapLoanTypeClick = {
                viewModel.swapLoanType()
            }
        )


        Spacer(modifier = Modifier.padding(10.dp))
        CalculatorTextFields(viewModel = viewModel)
        Spacer(modifier = Modifier.padding(20.dp))


        CalculateButton {
            viewModel.mapLoanData()
        }

        Spacer(modifier = Modifier.padding(30.dp))
        val loanResult by viewModel.loanResult.collectAsState()
        LoanResultCard(loanResult = loanResult) {
            onPaymentSchedulePressed()
        }
        Spacer(modifier = Modifier.padding(30.dp))
    }
}