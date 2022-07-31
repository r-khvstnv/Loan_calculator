package com.rssll971.loancalculator.ui.fragments.initial

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun LoanCardPreview(){
    LocalContext.current
    LoanCalcTheme {
        LoanCard(
            title = stringResource(id = R.string.title_annuity),
            description = stringResource(id = R.string.description_annuity),
            hint = stringResource(id = R.string.hint_annuity),
            paddingTop = 5.dp,
            paddingBottom = 5.dp,
            painterResource(id = R.drawable.ic_chart_flat),
            true
        ){
            Log.i("LCardPrev isAnnuity: ", it.toString())
        }
    }
}

@Composable
fun LoanCard(
    title: String,
    description: String,
    hint: String,
    paddingTop: Dp,
    paddingBottom: Dp,
    icon: Painter,
    isAnnuityLoan: Boolean,
    onClick: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .padding(20.dp, paddingTop, 20.dp, paddingBottom)
            .fillMaxWidth()
            .clickable { onClick(isAnnuityLoan) },
        shape = AbsoluteRoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 6.dp
    ) {
        Column {

            Text(
                text = title,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                textAlign = TextAlign.Start ,
                modifier = Modifier
                    .padding(0.dp, 16.dp, 80.dp, 0.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = AbsoluteCutCornerShape(0.dp, 0.dp, 40.dp, 0.dp)
                    )
                    .fillMaxWidth()
                    .padding(16.dp, 5.dp, 70.dp, 5.dp)
            )

            Image(
                painter = icon,
                contentDescription = "",
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                modifier = Modifier
                    .padding(20.dp, 30.dp, 20.dp, 40.dp)
                    .size(128.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 2.dp,
                modifier = Modifier.padding(20.dp, 30.dp, 20.dp, 5.dp)
            )

            Text(
                text = description,
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(22.dp, 0.dp)
                    .fillMaxWidth()

            )

            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 2.dp,
                modifier = Modifier.padding(20.dp, 5.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(20.dp, 5.dp, 20.dp, 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = hint,
                    color = MaterialTheme.colors.secondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 8.dp, 0.dp)
                        .weight(2f)
                )

                Text(
                    text = "Select",
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = AbsoluteRoundedCornerShape(4.dp)
                        )
                        .padding(16.dp, 10.dp)
                        .weight(1f)
                )
            }
        }
    }
}