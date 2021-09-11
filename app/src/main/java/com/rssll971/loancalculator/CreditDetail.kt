package com.rssll971.loancalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.rssll971.loancalculator.databinding.ActivityCreditDetailBinding
import kotlin.math.pow

class CreditDetail : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityCreditDetailBinding
    /**
     * Переменные
     */
    //ads
    private lateinit var mFirstSmartBannerAd: AdView
    private lateinit var mResultSmartBannerAd: AdView
    //адаптер для rv
    private lateinit var resultAdapter: MonthResultAdapter
    //массив с результатами вычислений
    private lateinit var resultList: ArrayList<MonthCreditModel>
    private lateinit var selectedCreditType: String
    //общие суммы выплат (необходимы для отображения в конце)
    private var interestOverall: Float = 0.0f
    private var amountOverall: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** Реклама */
        /*MobileAds.initialize(this) {}
        mFirstSmartBannerAd = findViewById(R.id.adView_detailsFirstBanner)
        mResultSmartBannerAd = findViewById(R.id.adView_detailsSecondBanner)
        val adRequest = AdRequest.Builder().build()

        mFirstSmartBannerAd.loadAd(adRequest)
        mResultSmartBannerAd.loadAd(adRequest)
        mFirstSmartBannerAd.adListener = object : AdListener(){
            override fun onAdClosed() {
                mFirstSmartBannerAd.loadAd(adRequest)
            }
        }
        mResultSmartBannerAd.adListener = object : AdListener(){
            override fun onAdClosed() {
                mFirstSmartBannerAd.loadAd(adRequest)
            }
        }


        *//** Получаем тип кредита с предыдущей страницы*//*
        selectedCreditType = intent?.getStringExtra("LoanType").toString()
        binding.tvSelectedCreditType.text = selectedCreditType
        *//** Скрываем сцену с результатами*//*
        binding.llResultTable.visibility = View.GONE


        *//**
         * Блок управления кнопками
         *//*
        //вычисление
        binding.llCalculate.setOnClickListener {
            //проверяем наличие данных
            if (binding.etAmount.text!!.trim().isNotEmpty() &&
                binding.etInterest.text!!.trim().isNotEmpty() &&
                binding.etLoanPeriod.text!!.trim().isNotEmpty()){
                //проверяем наличие периода отсрочки
                if (binding.etGracePeriod.text!!.trim().isEmpty()){
                    binding.etGracePeriod.setText("0")
                }

                *//**Переходим к вычеслениям относительно типа кредита*//*
                when(binding.tvSelectedCreditType.text){
                    resources.getString(R.string.st_annuity) -> {
                        estimateAnnuity(binding.etAmount.text.toString().toFloat(),
                            binding.etInterest.text.toString().toFloat(),
                            binding.etLoanPeriod.text.toString().toInt(),
                            binding.etGracePeriod.text.toString().toInt())
                    }
                    resources.getString(R.string.st_proportional) -> {
                        estimateDifferential(binding.etAmount.text.toString().toFloat(),
                                binding.etInterest.text.toString().toFloat(),
                                binding.etLoanPeriod.text.toString().toInt(),
                                binding.etGracePeriod.text.toString().toInt())
                    }
                }

                *//** Показываем таблицу с результатами*//*
                binding.llResultTable.visibility = View.VISIBLE


                *//**
                 * Сначала показываем конечную информацию,
                 * тк для Rv будет удален последний элемент в массиве
                *//*
                showTotalInfo()
                setupResultRecyclerView()
                //немного прокручиваем rv с таблицей
                binding.scrollView.requestChildFocus(binding.llResultTable, binding.llResultTable)
            } else{
                Toast.makeText(this, getString(R.string.st_add_data), Toast.LENGTH_LONG).show()
            }
        }
        //прокрутка вверх
        binding.ibUp.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }*/
    }

   /* *//**
     * Метод, который прячет клавиатуру,
     * когда пользователь совершил действие вне области клавиатуры
     *//*
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null){
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    *//**
     * Дальше 2 метода вычесления кредита
     *//*
    *//**
     * Ануитентный
     * действительность данных должно быть проверенно перед вызовом метода
     *//*
    private fun estimateAnnuity(loanAmount: Float, loanInterest: Float, loanPeriod: Int, loanGracePeriod: Int){
        var amount = loanAmount
        var gracePeriod = loanGracePeriod
        interestOverall = 0.0f
        amountOverall = 0.0f
        resultList = ArrayList()
        resultList.clear()
        val interest = loanInterest / 100.0f

        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRateInMonth: Float = interest / 12.0f
        //основной долг в месяц
        var mainAmountInMonth: Float
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float
        // создаем объект класса
        var monthInfo: MonthCreditModel


        *//**
         * вычисляем общую сумму выплаты в месяц
         * колво месяцев в строке ниже влияет на отсрочку *//*
        val generalAmountInMonth: Float = amount * (interestRateInMonth + (interestRateInMonth /
                        ((1 + interestRateInMonth).pow(loanPeriod - loanGracePeriod) - 1)))
        //цикл для вычислений каждого месяца
        for (i in 1..loanPeriod){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interest / 12)
            //суммируем проценты
            interestOverall += interestAmountInMonth
            //дальнейшие вычисления проводятся относительно
            // наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                *//**при отсрочки сумма основного долга до истечения срока не меняется*//*
                // создаем объект класса
                 monthInfo = MonthCreditModel(i, 0.0f,
                        interestAmountInMonth, interestAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
                //уменьшаем значение льготного периода
                gracePeriod--
            } else{
                //вычисляем основную сумму выплаты
                mainAmountInMonth = generalAmountInMonth - interestAmountInMonth
                //уменьшаем сумму основного долга
                amount -= mainAmountInMonth
                //убираем мелкую погрешность в последней строке
                if (i == loanPeriod)
                    amount = 0.0f

                //создаем объект класса
                monthInfo = MonthCreditModel(i, mainAmountInMonth,
                        interestAmountInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
            }
        }
        //подсчитываем общую сумму выплаты
        amountOverall = loanAmount + interestOverall
        //добавляем результат вычислений в конец массива
        monthInfo = MonthCreditModel(-1, loanAmount,
                interestOverall, amountOverall, 0.0f)
        resultList.add(monthInfo)
    }


    *//**
     * Дифференцированный
     * действительность данных должно быть проверенно перед вызовом метода
     *//*
    private fun estimateDifferential(loanAmount: Float,
                                     loanInterest: Float,
                                     loanPeriod: Int,
                                     loanGracePeriod: Int){
        var amount = loanAmount
        var gracePeriod = loanGracePeriod
        interestOverall = 0.0f
        amountOverall = 0.0f
        resultList = ArrayList()
        resultList.clear()
        val interest = loanInterest / 100.0f

        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRateInMonth: Float = interest / 12.0f
        //общая сумма выплаты в месяц
        var generalAmountInMonth: Float
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float
        // создаем объект класса
        var monthInfo: MonthCreditModel

        *//**
         * вычисляем общую сумму выплаты в месяц
         * колво месяцев в строке ниже влияет на отсрочку *//*
        val mainAmountInMonth: Float = amount / (loanPeriod - gracePeriod)
        //цикл для вычислений каждого месяца
        for (i in 1..loanPeriod){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interestRateInMonth)
            //суммируем проценты
            interestOverall += interestAmountInMonth

            //дальнейшие вычисления проводятся относительно
            // наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                *//**при отсрочки сумма основного долга до истечения срока не меняется*//*
                // создаем объект класса
                monthInfo = MonthCreditModel(i, 0.0f,
                        interestAmountInMonth, interestAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
                //уменьшаем значение льготного периода
                gracePeriod--
            } else{
                //вычисляем основную сумму выплаты
                generalAmountInMonth = mainAmountInMonth + interestAmountInMonth
                //уменьшаем сумму основного долга
                amount -= mainAmountInMonth
                //убираем мелкую погрешность в последней строке
                if (i == loanPeriod)
                    amount = 0.0f

                //создаем объект класса
                monthInfo = MonthCreditModel(i, mainAmountInMonth,
                        interestAmountInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
            }
        }
        //подсчитываем общую сумму выплаты
        amountOverall = loanAmount + interestOverall
        //добавляем результат вычислений в конец массива
        monthInfo = MonthCreditModel(-1, loanAmount,
                interestOverall, amountOverall, 0.0f)
        resultList.add(monthInfo)
    }


    *//**
     * Метод показа итоговой строки
     *//*
    private fun showTotalInfo(){
        val totalItem = resultList[resultList.size - 1]
        val mainMonthAmountAll= totalItem.mainMonthDebt
        val interestAll = totalItem.interestInMonth
        val generalMonthAmountAll = totalItem.generalAmountInMonth

        binding.tvTotalMainMonthAmount.text = String.format("%,.2f", mainMonthAmountAll)
        binding.tvTotalInterestInMonth.text = String.format("%,.2f", interestAll)
        binding.tvTotalGeneralAmountInMonth.text = String.format("%,.2f", generalMonthAmountAll)

        //ограничеваем максимальный размер rv если элементов больше 18
        if (resultList.size > 15)
            binding.rvDetailsContainer.layoutParams.height = resources.getDimension(R.dimen.dim_rv_height).toInt()
    }

    *//**
     * Метод показа результатов вычисления в Rv
     *//*
    private fun setupResultRecyclerView(){
        binding.rvDetailsContainer.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //передаем в адаптер массив без итоговой строки
        resultList.removeLast()
        //запускаем адаптер
        resultAdapter = MonthResultAdapter(resultList, this)
        binding.rvDetailsContainer.adapter = resultAdapter
    }*/
}