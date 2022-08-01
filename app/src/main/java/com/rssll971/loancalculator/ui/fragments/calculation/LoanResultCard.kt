package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.models.LoanResult
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en"
)
@Composable
fun LoanResultCardPreview(){
    LoanCalcTheme {
        val loanResult = LoanResult(
            true,
            100000000.123f,
            122000000.456f,
            48,
            25,
            22,
            emptyList()
        )

        LoanResultCard(loanResult = loanResult) {

        }
    }
}


@Composable
fun LoanResultCard(
    loanResult: LoanResult,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = AbsoluteRoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title_total),
                color = MaterialTheme.colors.primary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp, 0.dp, 0.dp)
            )

            val titleTextStyle = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
            )

            val valueTextStyle = TextStyle(
                color = MaterialTheme.colors.secondary,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
            val valueTextModifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp)

            Spacer(modifier = Modifier.padding(0.dp, 16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = "Loan Amount",
                        style = titleTextStyle,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = String.format("%,.2f", loanResult.amountInitial),
                        style = valueTextStyle,
                        modifier = valueTextModifier
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Payout Amount",
                        style = titleTextStyle,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = String.format("%,.2f", loanResult.amountFinal),
                        style = valueTextStyle,
                        modifier = valueTextModifier
                    )
                }
            }

            Spacer(modifier = Modifier.padding(0.dp, 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = "Interest Gross",
                        style = titleTextStyle,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = loanResult.interestGross.toString() + "%",
                        style = valueTextStyle,
                        modifier = valueTextModifier
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Interest Net",
                        style = titleTextStyle,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = loanResult.interestNet.toString() + "%",
                        style = valueTextStyle,
                        modifier = valueTextModifier
                    )
                }
            }

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 1.dp,
                modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 10.dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Period: ${loanResult.period} months",
                    style = titleTextStyle,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1.6f)
                )

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    shape = AbsoluteRoundedCornerShape(4.dp),
                    modifier = Modifier.weight(2f)
                ) {
                    Text(
                        text = "Payment Schedule",
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }

        }
    }
}