package com.rssll971.loancalculator

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.navigation.compose.rememberNavController
import com.rssll971.loancalculator.navigation.SetupNavHostController
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme
import com.rssll971.loancalculator.utils.Constants


class MainActivity : ComponentActivity() {
    //analytics TODO ACTIVATE
    //private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            LoanCalcTheme {
                SetupNavHostController(navController)
                Column {
                    //AdvertView()

                }

                if (savedInstanceState == null){
                    navController.navigate(Constants.DEST_INITIAL)
                }
            }
        }


        //аналитика TODO ACTIVATE
        //firebaseAnalytics = Firebase.analytics
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