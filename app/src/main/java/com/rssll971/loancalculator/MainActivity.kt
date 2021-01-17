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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Различные переменные
    //режим темы
    private var isNightMode: Boolean = false
    //Тип локализации
    private var isLocalisationEng: Boolean = true
    //Проверка на первый запуск активити
    private var isFirstRun: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Автоматическая смена темы согласно устройству
            //Проверяем не зыпускалась ли активити,
            //тк пользователь мог сменить ее до этого
        when(isFirstRun){
            false -> {/*ничего не изменяем, тк мы вернулись к этой активити*/}

            true -> {themeModeDefault()} //вводим именения, тк первый запуск
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
        adView_firstBanner.loadAd(adRequest)

        //Кнопки выбранного кредита
        //дополнительно передаем значение типа кредита
        ll_annuty.setOnClickListener {
            setCreditInfo(R.string.annuity)
        }
        ll_proportional.setOnClickListener {
            setCreditInfo(R.string.proportional)
        }


        //Кнопка смены темы
        tb_nightMode.setOnCheckedChangeListener { _, isChecked ->
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
        //Текущая тема на устройстве
        var currentNightMode = Configuration.UI_MODE_NIGHT_MASK
        //Меняем режим темы на аналогичный устройстве
        when(currentNightMode){
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
    private fun setCreditInfo(loanType: Int){
        //Запуск приложения успешно совершен
        //сообщаем это программе, чтобы при возращении обратно к этой активити
        //не возникли проблемы
        isFirstRun = false

        //Создаем новую активити
        val intent = Intent(this, CreditDetail::class.java)
        //TODO ПЕРЕДАТЬ ЗНАЧЕНИЯ ТЕМЫ И ЯЗЫКА
        //TODO ПЕРЕДАТЬ ТИП КРЕДИТА
        //запускаем новую активити
        startActivity(intent)
        //завершаем текущую активити
        finish()
    }


}