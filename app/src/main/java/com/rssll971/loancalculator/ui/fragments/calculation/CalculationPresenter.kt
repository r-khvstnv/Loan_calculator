package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.Context
import android.content.res.Resources
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.models.CreditModel
import com.rssll971.loancalculator.models.MonthCreditModel
import kotlin.math.pow

class CalculationPresenter: CalculationContract.Presenter {
    private var view: CalculationContract.CalculatorView? = null
    private var isAnnuity: Boolean = true
    override fun attach(view: CalculationContract.CalculatorView) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    /**Get chosen by user credit type from view*/
    override fun getCreditType(isAnnuity: Boolean, context: Context) {
        this.isAnnuity = isAnnuity

        val creditType = if (isAnnuity)
            context.getString(R.string.st_annuity)
        else
            context.getString(R.string.st_proportional)

        view?.showCreditType(creditType)
    }

    /**Validate entered data.
     * According to a good scenario, request calculator.
     * Otherwise request snackBar message*/
    override fun validateCreditData(
        loanAmount: String,
        interest: String,
        period: String,
        gracePeriod: String
    ) {
        if (loanAmount.isNotEmpty()
            && interest.isNotEmpty()
            && period.isNotEmpty()){
            var gracePeriodInt = 0
            if (gracePeriod.isNotEmpty())
                gracePeriodInt = gracePeriod.toInt()

            val creditModel = CreditModel(isAnnuity, loanAmount.toInt(),
                interest.toInt(), period.toInt(), gracePeriodInt)
            requestCreditEstimation(creditModel)
        } else{
            view?.showPoorDataErrorMessage()
        }
    }

    /**Request credit calculator according to the type of loan*/
    private fun requestCreditEstimation(creditModel: CreditModel){
        if (creditModel.isAnnuityType)
            estimateAnnuity(creditModel = creditModel)
        else
            estimateDifferential(creditModel = creditModel)
    }

    /**Next two fun calculate the credit tables*/
    private fun estimateAnnuity(creditModel: CreditModel){
        var amount: Float = creditModel.amount.toFloat()
        var gracePeriod = creditModel.gracePeriod
        val interest: Float = creditModel.interest / 100.0f

        var interestOverall = 0.0f

        val resultList: ArrayList<MonthCreditModel> = ArrayList()


        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRateInMonth: Float = interest / 12.0f
        //основной долг в месяц
        var mainAmountInMonth: Float
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float
        // создаем объект класса
        var monthInfo: MonthCreditModel


        /**
        * вычисляем общую сумму выплаты в месяц
        * колво месяцев в строке ниже влияет на отсрочку */
        val generalAmountInMonth: Float = amount * (interestRateInMonth + (interestRateInMonth /
                ((1 + interestRateInMonth).pow(creditModel.period - creditModel.gracePeriod) - 1)))
        //цикл для вычислений каждого месяца
        for (i in 1..creditModel.period){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interest / 12)
            //суммируем проценты
            interestOverall += interestAmountInMonth
            //дальнейшие вычисления проводятся относительно
            // наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                /**при отсрочки сумма основного долга до истечения срока не меняется*/
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
                if (i == creditModel.period)
                    amount = 0.0f

                //создаем объект класса
                monthInfo = MonthCreditModel(i, mainAmountInMonth,
                    interestAmountInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
            }
        }
        //calculate the total payment amount
        val amountOverall: Float = creditModel.amount + interestOverall
        //add result to the end of array
        val overall = MonthCreditModel(-1, creditModel.amount.toFloat(),
            interestOverall, amountOverall, 0.0f)
        providesCreditTableData(resultList, overall)
    }

    private fun estimateDifferential(creditModel: CreditModel){
        var amount: Float = creditModel.amount.toFloat()
        var gracePeriod = creditModel.gracePeriod
        val interest: Float = creditModel.interest / 100.0f

        var interestOverall = 0.0f

        val resultList: ArrayList<MonthCreditModel> = ArrayList()


        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRateInMonth: Float = interest / 12.0f
        //общая сумма выплаты в месяц
        var generalAmountInMonth: Float
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float
        // создаем объект класса
        var monthInfo: MonthCreditModel


        /**
         * вычисляем общую сумму выплаты в месяц
         * колво месяцев в строке ниже влияет на отсрочку */
        val mainAmountInMonth: Float = amount / (creditModel.period - creditModel.gracePeriod)
        //цикл для вычислений каждого месяца
        for (i in 1..creditModel.period){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interestRateInMonth)
            //суммируем проценты
            interestOverall += interestAmountInMonth
            //дальнейшие вычисления проводятся относительно
            // наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                /**при отсрочки сумма основного долга до истечения срока не меняется*/
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
                if (i == creditModel.period)
                    amount = 0.0f

                //создаем объект класса
                monthInfo = MonthCreditModel(i, mainAmountInMonth,
                    interestAmountInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                resultList.add(monthInfo)
            }
        }
        //подсчитываем общую сумму выплаты
        val amountOverall: Float = creditModel.amount + interestOverall
        //добавляем результат вычислений в конец массива
        val overall = MonthCreditModel(-1, creditModel.amount.toFloat(),
            interestOverall, amountOverall, 0.0f)

        providesCreditTableData(resultList, overall)
    }

    private fun providesCreditTableData(monthList: ArrayList<MonthCreditModel>,
                                        totalData: MonthCreditModel){
        val rvHeight = if (monthList.size <= 24)
            (monthList.size * 30).dp()
        else
            600.dp()
        view?.provideRecyclerViewHeight(rvHeight)
        view?.onEstimationCallback(monthList, totalData)

    }
    private fun Int.dp(): Int = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

