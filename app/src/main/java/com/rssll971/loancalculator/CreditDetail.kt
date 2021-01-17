package com.rssll971.loancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_credit_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class CreditDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_detail)
        //Реклама
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView_secondBanner.loadAd(adRequest)
    }
}