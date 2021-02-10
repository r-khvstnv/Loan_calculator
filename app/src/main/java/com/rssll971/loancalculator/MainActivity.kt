package com.rssll971.loancalculator

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.loancalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding
    //ads
    private lateinit var mSmartBannerAd: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getNightModeStatus()
        if (getNightModeStatus()){
            binding.tbNightMode.isChecked = true
        }





        //******************************************************************
        //TODO УБРАТЬ, ТК ТОЛЬКО ДЛЯ ТЕСТОВОЙ ВЕРСИИ
        val toast = Toast.makeText(this,
            "\nMade by: Ruslan Khvastunov\n\nClosed testing ONLY\n",
            Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        //******************************************************************

        //Реклама
        MobileAds.initialize(this) {}
        mSmartBannerAd = findViewById(R.id.adView_main_banner)
        val adRequest = AdRequest.Builder().build()
        mSmartBannerAd.loadAd(adRequest)
        mSmartBannerAd.adListener = object : AdListener(){
            override fun onAdClosed() {
                mSmartBannerAd.loadAd(adRequest)
            }
        }


        //Кнопки выбранного кредита
        //дополнительно передаем значение типа кредита
        binding.llAnnuity.setOnClickListener {
            setCreditInfo(binding.tvAnnuityTitle.text.toString())
        }
        binding.llProportional.setOnClickListener {
            setCreditInfo(binding.tvProportionalTitle.text.toString())
        }

        binding.ibInfo.setOnClickListener {
            showInfo()
        }

        binding.tbNightMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                //Меняем тему на темную
                themeModeToDark()
            } else {
                //Меняем тему на светлую
                themeModeToLight()
            }
        }
    }


    private fun themeModeToDark(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    //светлая
    private fun themeModeToLight(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    // установить соответсвенно той, что на устройстве
    private fun getNightModeStatus(): Boolean{
        //Меняем режим темы на аналогичный устройстве
        return when(resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK){
            //Светлая тема
            Configuration.UI_MODE_NIGHT_NO -> {
                false
            }
            //Темная тема
            Configuration.UI_MODE_NIGHT_YES -> {
                true
            }
            else -> false
        }
    }

    //Блок для перехода к заполению данных о кредите
    private fun setCreditInfo(loanType: String){
        //Создаем новую активити
        val intent = Intent(this, CreditDetail::class.java)
        intent.putExtra("LoanType", loanType)
        //запускаем новую активити
        startActivity(intent)
    }


    //настройки
    private fun showInfo(){
        val dialogSettings = Dialog(this)
        dialogSettings.setContentView(R.layout.dialog_settings)
        //чтобы применить кастомный фон
        dialogSettings.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogSettings.show()
    }
}