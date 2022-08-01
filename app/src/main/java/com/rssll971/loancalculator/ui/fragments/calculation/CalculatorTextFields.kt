package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Composable
fun CalculatorTextFieldsPreview(){
    LoanCalcTheme {
        CalculatorTextFields(viewModel = viewModel())

    }
}

@Composable
fun CalculatorTextFields(
    viewModel: LoanCalculationViewModel
){
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .fillMaxWidth()
    ) {

        val amount by viewModel.typedLoanAmount.collectAsState()
        val interest by viewModel.typedLoanInterest.collectAsState()
        val period by viewModel.typedLoanPeriod.collectAsState()
        val gracePeriod by viewModel.typedLoanGracePeriod.collectAsState()
        val initialFee by viewModel.typedLoanInitialFee.collectAsState()


        val textFieldsModifier = Modifier
            .padding(0.dp, 12.dp)
            .fillMaxWidth()
        val tFldKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)

        OutlinedTextField(
            value = amount,
            onValueChange = viewModel::setTypedLoanAmount,
            label = { Text(text = stringResource(id = R.string.title_loan_amount))},
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier,
        )

        OutlinedTextField(
            value = interest,
            onValueChange = {if(it.length <= 2) viewModel.setTypedLoanInterest(it)},
            label = { Text(text = stringResource(id = R.string.title_interest))},
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier
        )

        OutlinedTextField(
            value = period,
            onValueChange = viewModel::setTypedLoanPeriod,
            label = { Text(text = stringResource(id = R.string.title_period))},
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 30.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 2.dp,
                modifier = Modifier.weight(0.2f)
            )
            Text(
                text = stringResource(id = R.string.title_optional),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.8f)
                    .padding(5.dp, 0.dp)
            )
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 2.dp,
                modifier = Modifier.weight(1.8f)
            )
        }

        OutlinedTextField(
            value = gracePeriod,
            onValueChange = viewModel::setTypedLoanGracePeriod,
            label = { Text(text = stringResource(id = R.string.title_grace_period))},
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier
        )

        OutlinedTextField(
            value = initialFee,
            onValueChange = viewModel::setTypedLoanInitialFee,
            label = { Text(text = stringResource(id = R.string.title_initial_fee))},
            keyboardOptions = tFldKeyboardOptions,
            modifier = textFieldsModifier
        )


    }

}
