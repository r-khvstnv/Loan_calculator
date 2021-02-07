package com.rssll971.loancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.rssll971.loancalculator.databinding.ActivityCreditDetailBinding

class CreditDetail : AppCompatActivity() {
    private lateinit var binding: ActivityCreditDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Реклама
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adViewDetailsFirstBanner.loadAd(adRequest)
        binding.adViewDetailsSecondBanner.loadAd(adRequest)

    }
}