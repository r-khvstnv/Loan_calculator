package com.rssll971.loancalculator.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.rssll971.loancalculator.R


@Composable
fun AdvertView(){
    val width = LocalConfiguration.current.screenWidthDp
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            val adRequest = AdRequest.Builder().build()
            AdView(context).apply {
                val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width)
                setAdSize(adSize)
                adUnitId = context.getString(R.string.ad_banner_id_main)
                loadAd(adRequest)
                adListener = object : AdListener() {
                    override fun onAdClosed() {
                        loadAd(adRequest)
                    }
                }
            }
        },
    )
}