package com.rssll971.loancalculator.ui.fragments.initial

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun InitialPreview(){
    LoanCalcTheme {
        InitialUi{
            Log.i("InitialPrev isAnnuity: ", it.toString())
        }
    }
}

/**Method shows all available loan types*/
@Composable
fun InitialUi(
    onLoanCardClick: (Boolean) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        /**Row with title and info*/
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                .fillMaxWidth()
        ) {
            TopTitleText()
            InfoImage()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            /**Annuity credit type*/
            LoanCard(
                title = stringResource(id = R.string.title_annuity),
                description = stringResource(id = R.string.description_annuity),
                hint = stringResource(id = R.string.hint_annuity),
                paddingTop = 50.dp,
                paddingBottom = 10.dp,
                painterResource(id = R.drawable.ic_chart_flat),
                true
            ){
                onLoanCardClick(it)
            }

            /**Differential credit type*/
            LoanCard(
                title = stringResource(id = R.string.title_differential),
                description = stringResource(id = R.string.description_differential),
                hint = " ",
                paddingTop = 20.dp,
                paddingBottom = 60.dp,
                painterResource(id = R.drawable.ic_chart_downtrend),
                false
            ){
                onLoanCardClick(it)
            }
        }
    }
}

/**Method shows styled title*/
@Composable
fun TopTitleText(){
    Text(
        text = stringResource(id = R.string.title_select_loan_type),
        color = MaterialTheme.colors.onPrimary,
        fontSize = 28.sp,
        textAlign = TextAlign.Start ,
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = AbsoluteCutCornerShape(0.dp, 0.dp, 50.dp, 0.dp)
            )
            .padding(20.dp, 10.dp, 70.dp, 10.dp)
    )
}

/**Method shows InfoImageView for info dialog access*/
@Composable
fun InfoImage(){
    Image(
        painter = painterResource(id = R.drawable.ic_info),
        contentDescription = "Info",
        alignment = Alignment.CenterEnd,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .size(30.dp)
    )
}