package com.rssll971.loancalculator

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.rssll971.loancalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding
    //Различные переменные
    //режим темы
    private var isNightMode: Boolean = false
    //Тип локализации
    private var isLocalisationEng: Boolean = true
    //Проверка на первый запуск активити
    private var isFirstRun: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Автоматическая смена темы согласно устройству
            //Проверяем не зыпускалась ли активити,
            //тк пользователь мог сменить ее до этого
        when(isFirstRun){
            false -> {/*ничего не изменяем, тк мы вернулись к этой активити*/}

            true -> {themeModeDefault()} //вводим изменения, тк первый запуск
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


        //Кнопка смены темы
        binding.tbNightMode.setOnCheckedChangeListener { _, isChecked ->
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

    //**************************************************************************
    //БЛОК СМЕНЫ ТЕМЫ СВЕТЛАЯ/ТЕМНАЯ
    //темная
    private fun themeModeToDark(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        isNightMode = true
    }

    //светлая
    private fun themeModeToLight(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        isNightMode = false
    }

    // установить соответсвенно той, что на устройстве
    private fun themeModeDefault(){
        //Меняем режим темы на аналогичный устройстве
        when(Configuration.UI_MODE_NIGHT_MASK){
            //Светлая тема
            Configuration.UI_MODE_NIGHT_NO -> {
                isNightMode = false
                themeModeToLight()
            }
            //Темная тема
            Configuration.UI_MODE_NIGHT_YES -> {
                isNightMode = true
                themeModeToDark()
            }
        }
    }
    //--------------------------------------------------------------------
    //TODO МЕНЯТЬ ЛОКАЛИЗАЦИЮ СООТВЕТСВЕННО ЯЗЫКУ НА ТЕЛЕФОНЕ
    //TODO СДЕЛАТЬ ЛОКАЛИЗАЦИЮ


    //Блок для перехода к заполению данных о кредите
    private fun setCreditInfo(loanType: String){
        //Запуск приложения успешно совершен
        //сообщаем это программе, чтобы при возращении обратно к этой активити
        //не возникли проблемы
        isFirstRun = false

        //Создаем новую активити
        val intent = Intent(this, CreditDetail::class.java)
        intent.putExtra("LoanType", loanType)
        //TODO ПЕРЕДАТЬ ТИП КРЕДИТА
        //запускаем новую активити
        startActivity(intent)
    }


}