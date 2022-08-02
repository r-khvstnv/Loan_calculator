package com.rssll971.loancalculator.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rssll971.loancalculator.SharedViewModel
import com.rssll971.loancalculator.models.MonthlyPayment
import com.rssll971.loancalculator.ui.composable.AdvertView
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Composable
fun DetailsContentPreview(){
    LoanCalcTheme {
        val list = mutableListOf<MonthlyPayment>()
        for (i in 0..36){
            val monthlyPayment = MonthlyPayment(
                i+1, 1135589.256f, 1250000.123f,
                23855890.456f, 37171201.789f
            )
            list.add(monthlyPayment)
        }

        DetailsContent(monthlyPaymentList = list)
    }
}


@Composable
fun DetailsScreen(
    viewModel: SharedViewModel
){
    val loanResult by viewModel.loanResult.collectAsState()
    Column {
        AdvertView()
        DetailsContent(monthlyPaymentList = loanResult.monthlyPayments)
    }
}

@Composable
fun DetailsContent(monthlyPaymentList: List<MonthlyPayment>) {
    val state = rememberLazyListState()
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        MonthlyPaymentTitle()
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