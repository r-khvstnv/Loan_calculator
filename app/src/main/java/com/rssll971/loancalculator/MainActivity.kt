package com.rssll971.loancalculator

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.loancalculator.databinding.ActivityMainBinding
import com.rssll971.loancalculator.ui.fragments.calculation.LoanCalculationFragment
import com.rssll971.loancalculator.ui.fragments.details.DetailsFragment
import com.rssll971.loancalculator.ui.fragments.initial.InitialFragment
import com.rssll971.loancalculator.utils.Constants

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding
    //ads
    private lateinit var mSmartBannerAd: AdView
    //analytics TODO ACTIVATE
    //private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //аналитика TODO ACTIVATE
        //firebaseAnalytics = Firebase.analytics


        /** Ads */
        MobileAds.initialize(this) {}
        mSmartBannerAd = findViewById(R.id.adView_main)
        val adRequest = AdRequest.Builder().build()
        mSmartBannerAd.loadAd(adRequest)
        mSmartBannerAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mSmartBannerAd.loadAd(adRequest)
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.graph = navController.createGraph(
            startDestination = Constants.DEST_INITIAL
        ){
            fragment<InitialFragment>(Constants.DEST_INITIAL)

            fragment<LoanCalculationFragment>(
                "${Constants.DEST_CALCULATION}/{${Constants.ARGS_IS_ANNUITY}}"
            ){
                argument(Constants.ARGS_IS_ANNUITY){
                    type = NavType.BoolType
                }
            }

            fragment<DetailsFragment>(Constants.DEST_CALCULATION)
        }
    }

    /**Hide keyboard on outside click*/
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null){
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}