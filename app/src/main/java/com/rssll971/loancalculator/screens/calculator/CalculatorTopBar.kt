/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.loancalculator.screens.calculator

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.composable.IconButtonSmall
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme
import com.rssll971.loancalculator.utils.isRuCurrLocale

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    widthDp = 360,
    locale = "en"
)
@Composable
fun CalculatorTopBarPreview(){
    LoanCalcTheme {
        CalculatorTopBar(
            stringResource(id = R.string.title_annuity),
            onCloseClick = {},
            onSwapLoanTypeClick = {}
        )
    }
}

@Composable
fun CalculatorTopBar(
    loanType: String,
    onCloseClick: () -> Unit,
    onSwapLoanTypeClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.calculate_field_margin_horizontal))
            .fillMaxWidth()
    ) {

        //Close icon button
        IconButtonSmall(
            icon = painterResource(id = R.drawable.ic_close),
            onClick = onCloseClick
        )

        //Loan Type
        Text(
            text = loanType,
            textAlign = TextAlign.Center,
            fontSize = if (isRuCurrLocale()) 20.sp else 28.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(10.dp, 0.dp)
        )

        //Flip loan type icon button
        IconButtonSmall(
            icon = painterResource(id = R.drawable.ic_swap),
            onClick = onSwapLoanTypeClick
        )
    }
}