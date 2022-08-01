package com.rssll971.loancalculator.ui.fragments.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rssll971.loancalculator.models.MonthlyPayment
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Composable
fun ResultMonthlyPaymentLazyColumnPreview(){
    LoanCalcTheme {
        val list = mutableListOf<MonthlyPayment>()
        for (i in 0..10){
            val monthlyPayment = MonthlyPayment(
                1, 1135589.256f, 1250000.123f,
                23855890.456f, 37171201.789f
            )
            val monthlyPayment12 = MonthlyPayment(
                12, 1135589.256f, 1250000.123f,
                23855890.456f, 37171201.789f
            )
            list.add(monthlyPayment)
            list.add(monthlyPayment12)
        }

        ResultMonthlyPaymentLazyColumn(monthlyPaymentList = list)
    }
}

@Composable
fun ResultMonthlyPaymentLazyColumn(monthlyPaymentList: List<MonthlyPayment>) {
    val state = rememberLazyListState()
    Column (
        modifier = Modifier.fillMaxWidth().height(500.dp)
    ){
        ResultColumnsTitle()
        LazyColumn (
            state = state,
            modifier = Modifier.fillMaxWidth()
        ){
            items(monthlyPaymentList){ monthlyPayment ->
                MonthlyPaymentRow(monthlyPayment = monthlyPayment)
            }
        }
    }
}