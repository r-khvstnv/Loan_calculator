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
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.rssll971.loancalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)





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
        val adRequest = AdRequest.Builder().build()
        binding.adViewFirstBanner.loadAd(adRequest)

        //Кнопки выбранного кредита
        //дополнительно передаем значение типа кредита
        binding.llAnnuity.setOnClickListener {
            setCreditInfo(binding.tvAnnuityTitle.text.toString())
        }
        binding.llProportional.setOnClickListener {
            setCreditInfo(binding.tvProportionalTitle.text.toString())
        }

        binding.ibSettings.setOnClickListener {
            showSettings()
        }





    }

    //**************************************************************************
    //БЛОК СМЕНЫ ТЕМЫ СВЕТЛАЯ/ТЕМНАЯ
    //темная
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
    //--------------------------------------------------------------------
    //TODO МЕНЯТЬ ЛОКАЛИЗАЦИЮ СООТВЕТСВЕННО ЯЗЫКУ НА ТЕЛЕФОНЕ
    //TODO СДЕЛАТЬ ЛОКАЛИЗАЦИЮ


    //Блок для перехода к заполению данных о кредите
    private fun setCreditInfo(loanType: String){
        //Создаем новую активити
        val intent = Intent(this, CreditDetail::class.java)
        intent.putExtra("LoanType", loanType)
        //запускаем новую активити
        startActivity(intent)
    }


    //настройки
    private fun showSettings(){
        val dialogSettings = Dialog(this)
        dialogSettings.setContentView(R.layout.dialog_settings)
        //чтобы применить кастомный фон
        dialogSettings.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tbNightMode = dialogSettings.findViewById<ToggleButton>(R.id.tb_nightMode)
        var tbLanguage = dialogSettings.findViewById<ToggleButton>(R.id.tb_language)

        dialogSettings.show()
        getNightModeStatus()
        if (getNightModeStatus()){
            tbNightMode.isChecked = true
        }

        Log.i("NightMode", "${getNightModeStatus()}")
        tbNightMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                //Меняем тему на темную
                themeModeToDark()
            } else {
                //Меняем тему на светлую
                themeModeToLight()
            }
        }
        //TODO СОЗДАТЬ СМЕНУ ЯЗЫКА И ЛОКАЛИЗАЦИЮ
    }


}