package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    widthDp = 320
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
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        IconButton(
            onClick = { onCloseClick() },
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = loanType,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(10.dp, 0.dp)
        )

        IconButton(
            onClick = { onSwapLoanTypeClick() },
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_swap),
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxSize()
            )
        }


    }
}