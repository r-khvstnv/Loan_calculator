package com.rssll971.loancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.rssll971.loancalculator.databinding.ActivityCreditDetailBinding
import kotlin.math.pow

class CreditDetail : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityCreditDetailBinding
    //адаптер для rv
    private lateinit var resultAdapter: MonthResultAdapter
    //массив с результатами вычислений
    private lateinit var resultList: ArrayList<MonthCreditInfo>
    private lateinit var selectedCreditType: String
    //общие суммы выплат (необходимы для отображения в конце)
    private var interestOverall: Float = 0.0f
    private var amountOverall: Float = 0.0f

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


        //выставляем тип кредита
        selectedCreditType = intent?.getStringExtra("LoanType").toString()
        binding.tvSelectedCreditType.text = selectedCreditType
        //скрываем сцену с результатом
        binding.llResultTable.visibility = View.GONE




        //todo переделать
        binding.llCalculate.setOnClickListener {
            //проверяем наличие данных
            if (binding.etAmount.text.trim().isNotEmpty() &&
                binding.etInterest.text.trim().isNotEmpty() &&
                binding.etLoanPeriod.text.trim().isNotEmpty()){
                //проверяем наличие отсрочки
                if (binding.etGracePeriod.text.trim().isEmpty()){
                    binding.etGracePeriod.setText("0")
                }

                //переходим к вычеслениям относительно типа кредита
                when(binding.tvSelectedCreditType.text){
                    resources.getString(R.string.st_annuity) -> {
                        estimateAnnuity(binding.etAmount.text.toString().toFloat(),
                            binding.etInterest.text.toString().toFloat(),
                            binding.etLoanPeriod.text.toString().toInt(),
                            binding.etGracePeriod.text.toString().toInt())
                    }
                    resources.getString(R.string.st_proportional) -> {
                        estimateProportional(binding.etAmount.text.toString().toFloat(),
                                binding.etInterest.text.toString().toFloat(),
                                binding.etLoanPeriod.text.toString().toInt(),
                                binding.etGracePeriod.text.toString().toInt())
                    }
                }

                //показываем скрытую сцену с результатом
                binding.llResultTable.visibility = View.VISIBLE


                //сначала показываем конечную информацию,
                // тк для Rv будет удален последний элемент в массиве
                showTotalInfo()
                setupResultRecyclerView()
                //немного прокручиваем сцену
                binding.scrollView.requestChildFocus(binding.llResultTable, binding.llResultTable)

            }
            else{
                Toast.makeText(this, "Change it to values", Toast.LENGTH_LONG).show()
            }
        }

        //закрыть активити
        binding.llMainPage.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, 0)
        }
    }

    //вычисление кредитов
    //ануитентный
    //действительность данных должно быть проверенно перед вызовом метода
    private fun estimateAnnuity(loanAmount: Float, loanInterest: Float, loanPeriod: Int, loanGracePeriod: Int){
        var amount = loanAmount
        var gracePeriod = loanGracePeriod
        interestOverall = 0.0f
        amountOverall = 0.0f
        resultList = ArrayList<MonthCreditInfo>()
        resultList.clear()
        val interest = loanInterest / 100.0f

        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRateInMonth: Float = interest / 12.0f
        //общая сумма выплаты в месяц
        var generalAmountInMonth: Float = 0.0f
        //основной долг в месяц
        var mainAmountInMonth: Float = 0.0f
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float = 0.0f

        // создаем объект класса
        var monthInfo: MonthCreditInfo

        //вычисляем общую сумму выплаты в месяц
        //колво месяцев в строке ниже влияет на отсрочку
        generalAmountInMonth = amount * (interestRateInMonth +
                (interestRateInMonth / ((1 + interestRateInMonth).pow(loanPeriod - loanGracePeriod) - 1)))

        //цикл для вычислений каждого месяца
        for (i in 1..loanPeriod){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interest / 12)
            //суммируем проценты
            interestOverall += interestAmountInMonth

            //дальнейшие вычисления проводятся относительно наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                //при отсрочки сумма основного долга до истечения срока не меняется

                // создаем объект класса
                 monthInfo = MonthCreditInfo(i, 0.0f,
                        interestAmountInMonth, interestAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
                //уменьшаем значение льготного периода
                gracePeriod--
            }
            else{
                //вычисляем основную сумму выплаты
                mainAmountInMonth = generalAmountInMonth - interestAmountInMonth
                //уменьшаем сумму основного долга
                amount -= mainAmountInMonth

                //убираем мелкую погрешность в последней строке
                if (i == loanPeriod){
                    amount = 0.0f
                }

                //создаем объект класса
                monthInfo = MonthCreditInfo(i, mainAmountInMonth,
                        interestAmountInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
            }
        }

        //подсчитываем общую сумму выплаты
        amountOverall = loanAmount + interestOverall
        //добавляем результат вычислений в конец массива
        monthInfo = MonthCreditInfo(-1, loanAmount,
                interestOverall, amountOverall, 0.0f)
        resultList.add(monthInfo)
    }


    //вычисление кредитов
    //ануитентный
    //действительность данных должно быть проверенно перед вызовом метода
    private fun estimateProportional(loanAmount: Float, loanInterest: Float, loanPeriod: Int, loanGracePeriod: Int){
        var amount = loanAmount
        var gracePeriod = loanGracePeriod
        interestOverall = 0.0f
        amountOverall = 0.0f
        resultList = ArrayList<MonthCreditInfo>()
        resultList.clear()
        val interest = loanInterest / 100.0f

        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRateInMonth: Float = interest / 12.0f
        //общая сумма выплаты в месяц
        var generalAmountInMonth: Float = 0.0f
        //основной долг в месяц
        var mainAmountInMonth: Float = 0.0f
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float = 0.0f

        // создаем объект класса
        var monthInfo: MonthCreditInfo

        //вычисляем общую сумму выплаты в месяц
        //колво месяцев в строке ниже влияет на отсрочку
        mainAmountInMonth = amount / (loanPeriod - gracePeriod)

        //цикл для вычислений каждого месяца
        for (i in 1..loanPeriod){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interestRateInMonth)
            //суммируем проценты
            interestOverall += interestAmountInMonth

            //дальнейшие вычисления проводятся относительно наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                //при отсрочки сумма основного долга до истечения срока не меняется

                // создаем объект класса
                monthInfo = MonthCreditInfo(i, 0.0f,
                        interestAmountInMonth, interestAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
                //уменьшаем значение льготного периода
                gracePeriod--
            }
            else{
                //вычисляем основную сумму выплаты
                generalAmountInMonth = mainAmountInMonth + interestAmountInMonth

                //уменьшаем сумму основного долга
                amount -= mainAmountInMonth

                //убираем мелкую погрешность в последней строке
                if (i == loanPeriod){
                    amount = 0.0f
                }

                //создаем объект класса
                monthInfo = MonthCreditInfo(i, mainAmountInMonth,
                        interestAmountInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
            }
        }
        //подсчитываем общую сумму выплаты
        amountOverall = loanAmount + interestOverall
        //добавляем результат вычислений в конец массива
        monthInfo = MonthCreditInfo(-1, loanAmount,
                interestOverall, amountOverall, 0.0f)
        resultList.add(monthInfo)
    }

    //показать итоговую строку
    private fun showTotalInfo(){
        val totalItem = resultList[resultList.size - 1]
        val mainMonthAmountAll= totalItem.getMainMonthDebt()
        val interestAll = totalItem.getInterestInMonth()
        val generalMonthAmountAll = totalItem.getGeneralAmountInMonth()

        binding.tvTotalMainMonthAmount.text = String.format("%.2f", mainMonthAmountAll)
        binding.tvTotalInterestInMonth.text = String.format("%.2f", interestAll)
        binding.tvTotalGeneralAmountInMonth.text = String.format("%.2f", generalMonthAmountAll)
    }

    //показать результат
    private fun setupResultRecyclerView(){
        binding.rvDetailsContainer.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //передаем в адаптер массив без итоговой строки
        resultList.removeLast()
        //запускаем адаптер
        resultAdapter = MonthResultAdapter(resultList, this)
        binding.rvDetailsContainer.adapter = resultAdapter
    }
}