package com.rssll971.loancalculator.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.databinding.ActivityMainBinding
import com.rssll971.loancalculator.di.component.DaggerActivityComponent
import com.rssll971.loancalculator.di.module.ActivityModule
import com.rssll971.loancalculator.ui.fragments.initial.InitialFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {
    @Inject
    lateinit var presenter: MainPresenter
    //binding
    private lateinit var binding: ActivityMainBinding
    //ads
    private lateinit var mSmartBannerAd: AdView
    //analytics
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        injector()
        presenter.attach(this)
        /*//аналитика todo enable
        firebaseAnalytics = Firebase.analytics
        */


        /** Реклама */
        MobileAds.initialize(this) {}
        mSmartBannerAd = findViewById(R.id.adView_main)
        val adRequest = AdRequest.Builder().build()
        mSmartBannerAd.loadAd(adRequest)
        mSmartBannerAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mSmartBannerAd.loadAd(adRequest)
            }
        }

        supportFragmentManager.beginTransaction().apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.ll_container, InitialFragment())
            setReorderingAllowed(true)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }


    private fun injector(){
        val injectorMainComponent = DaggerActivityComponent.builder().activityModule(ActivityModule(this)).build()
        injectorMainComponent.inject(this)
    }




}