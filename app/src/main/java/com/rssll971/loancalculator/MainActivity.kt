package com.rssll971.loancalculator

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.rssll971.loancalculator.navigation.SetupNavHostController
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme
import com.rssll971.loancalculator.utils.Constants


class MainActivity : ComponentActivity() {
    //analytics
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = Firebase.analytics

        setContent {
            val navController = rememberNavController()
            LoanCalcTheme {
                SetupNavHostController(navController)

                if (savedInstanceState == null){
                    navController.navigate(Constants.DEST_INITIAL)
                }
            }
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