package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun CalculateButtonPreview(){
    LoanCalcTheme {
        val modifier = Modifier.padding(16.dp, 5.dp)
        CalculateButton(
            modifier = modifier) {

        }
    }
}

@Composable
fun CalculateButton(
    modifier: Modifier,
    onCalculateClick: () -> Unit
){
    Button(
        onClick = onCalculateClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
        shape = AbsoluteRoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.action_calculate),
            fontSize = 22.sp,
            color = MaterialTheme.colors.onSecondary
        )
    }
}