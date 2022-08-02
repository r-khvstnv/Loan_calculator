package com.rssll971.loancalculator

import androidx.lifecycle.ViewModel
import com.rssll971.loancalculator.models.Loan
import com.rssll971.loancalculator.models.LoanResult
import com.rssll971.loancalculator.models.MonthlyPayment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.pow

class SharedViewModel: ViewModel() {
    private var _typedLoanAmount = MutableStateFlow("123456")
    val typedLoanAmount: StateFlow<String> get() = _typedLoanAmount
    private var _typedLoanInterest = MutableStateFlow("1")
    val typedLoanInterest: StateFlow<String> get() = _typedLoanInterest
    private var _typedLoanPeriod = MutableStateFlow("5")
    val typedLoanPeriod: StateFlow<String> get() = _typedLoanPeriod
    private var _typedLoanGracePeriod = MutableStateFlow("")
    val typedLoanGracePeriod: StateFlow<String> get() = _typedLoanGracePeriod
    private var _typedLoanInitialFee = MutableStateFlow("")
    val typedLoanInitialFee: StateFlow<String> get() = _typedLoanInitialFee

    private var _isAnnuity = MutableStateFlow(true)
    val isAnnuity: StateFlow<Boolean> get() = _isAnnuity
    //private var _isFieldMissed  = MutableStateFlow(false)
    //val isFieldMissed: StateFlow<Boolean> get() = _isFieldMissed

    private var _loanResult = MutableStateFlow(LoanResult())
    val loanResult: StateFlow<LoanResult> get() = _loanResult


    fun setLoanType(isAnnuity: Boolean){
        _isAnnuity.value = isAnnuity
    }
    fun swapLoanType(){
        _isAnnuity.value = !_isAnnuity.value
        _loanResult.value = LoanResult()
    }

    fun setTypedLoanAmount(value: String){
        _typedLoanAmount.value = value
    }
    fun setTypedLoanInterest(value: String){
        _typedLoanInterest.value = value
    }
    fun setTypedLoanPeriod(value: String){
        _typedLoanPeriod.value = value
    }
    fun setTypedLoanGracePeriod(value: String){
        _typedLoanGracePeriod.value = value
    }
    fun setTypedLoanInitialFee(value: String){
        _typedLoanInitialFee.value = value
    }

    private fun isUserInputIsValid(): Boolean{
        return when{
            _typedLoanAmount.value.isEmpty() -> false
            _typedLoanInterest.value.isEmpty() -> false
            _typedLoanPeriod.value.isEmpty() -> false
            else -> true
        }
    }

    fun mapLoanData(){
        if (!isUserInputIsValid()){
            //_isFieldMissed.value = true
        } else{

            val gracePeriod = if (_typedLoanGracePeriod.value.isNotEmpty()) {
                _typedLoanGracePeriod.value.toInt()
            } else{
                0
            }
            val initialFee = if (_typedLoanInitialFee.value.isNotEmpty()) {
                _typedLoanInitialFee.value.toInt()
            } else{
                0
            }

            val loan = Loan(
                isAnnuityType = _isAnnuity.value,
                amount = _typedLoanAmount.value.toInt(),
                interest = _typedLoanInterest.value.toInt(),
                period = _typedLoanPeriod.value.toInt(),
                gracePeriod = gracePeriod,
                initialFee = initialFee,
                isInitialFeeInPercent = true
            )

            if (loan.isAnnuityType)
                estimateAsAnnuityLoan(loan)
            else
                estimateAsDifferentialLoan(loan)

        }
    }

    private fun estimateAsAnnuityLoan(loan: Loan){
        val initialFee: Float = loan.amount * (loan.initialFee / 100f)
        var amount: Float = loan.amount - initialFee
        var gracePeriod = loan.gracePeriod
        val interest: Float = loan.interest / 100.0f

        var interestAmountOverall = 0.0f //todo interest total

        val monthlyPaymentList: ArrayList<MonthlyPayment> = ArrayList()

        if (loan.initialFee > 0){
            val monthlyPayment = MonthlyPayment(
                0, 0f, 0f,
                totalAmount = initialFee, remainder = amount
            )
            monthlyPaymentList.add(monthlyPayment)
        }


        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRatePerMonth: Float = interest / 12.0f
        //основной долг в месяц
        var mainDebtInMonth: Float
        //выплата процента по кредиту в месяц
        var interestDebtInMonth: Float
        // создаем объект класса
        var monthlyPayment: MonthlyPayment


        /**
         * вычисляем общую сумму выплаты в месяц
         * колво месяцев в строке ниже влияет на отсрочку */
        val generalAmountInMonth: Float = amount * (interestRatePerMonth + (interestRatePerMonth /
                ((1 + interestRatePerMonth).pow(loan.period - loan.gracePeriod) - 1)))

        //цикл для вычислений каждого месяца
        for (i in 1..loan.period){
            //доля процентов в месячной выплате
            interestDebtInMonth = amount * (interest / 12)
            //суммируем проценты
            interestAmountOverall += interestDebtInMonth
            //дальнейшие вычисления проводятся относительно
            // наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                /**при отсрочки сумма основного долга до истечения срока не меняется*/
                // создаем объект класса
                monthlyPayment = MonthlyPayment(i, 0.0f,
                    interestDebtInMonth, interestDebtInMonth, amount)
                //добавляем объект в массив
                monthlyPaymentList.add(monthlyPayment)
                //уменьшаем значение льготного периода
                gracePeriod--
            } else{
                //вычисляем основную сумму выплаты
                mainDebtInMonth = generalAmountInMonth - interestDebtInMonth
                //уменьшаем сумму основного долга
                amount -= mainDebtInMonth
                //убираем мелкую погрешность в последней строке
                if (i == loan.period)
                    amount = 0.0f

                //создаем объект класса
                monthlyPayment = MonthlyPayment(i, mainDebtInMonth,
                    interestDebtInMonth, generalAmountInMonth, amount)
                //добавляем объект в массив
                monthlyPaymentList.add(monthlyPayment)
            }
        }

        //calculate the total payment amount
        val amountFinal: Float = loan.amount + interestAmountOverall
        val interestNet: Int = ((amountFinal / loan.amount - 1) * 100f).toInt()

        val loanResult = LoanResult(
            isAnnuityType = true,
            amountInitial = loan.amount.toFloat(),
            amountFinal = amountFinal,
            period = loan.period,
            interestGross = loan.interest,
            interestNet = interestNet,
            monthlyPayments = monthlyPaymentList
        )
        _loanResult.value = loanResult
    }

    private fun estimateAsDifferentialLoan(loan: Loan){
        val initialFee: Float = loan.amount * (loan.initialFee / 100f)
        var amount: Float = loan.amount - initialFee
        var gracePeriod = loan.gracePeriod
        val interest: Float = loan.interest / 100.0f

        var interestAmountOverall = 0.0f

        val monthlyPaymentList: ArrayList<MonthlyPayment> = ArrayList()

        if (loan.initialFee > 0){
            val monthlyPayment = MonthlyPayment(
                0, 0f, 0f,
                totalAmount = initialFee, remainder = amount
            )
            monthlyPaymentList.add(monthlyPayment)
        }


        //процентная ставка в месяц
        //делим на 12 тк процент указан годовой
        val interestRatePerMonth: Float = interest / 12.0f
        //общая сумма выплаты в месяц
        var totalAmountInMonth: Float
        //выплата процента по кредиту в месяц
        var interestAmountInMonth: Float
        // создаем объект класса
        var monthlyPayment: MonthlyPayment


        /**
         * вычисляем общую сумму выплаты в месяц
         * колво месяцев в строке ниже влияет на отсрочку */
        val mainDebtInMonth: Float = amount / (loan.period - loan.gracePeriod)

        //цикл для вычислений каждого месяца
        for (i in 1..loan.period){
            //доля процентов в месячной выплате
            interestAmountInMonth = amount * (interestRatePerMonth)
            //суммируем проценты
            interestAmountOverall += interestAmountInMonth
            //дальнейшие вычисления проводятся относительно
            // наличия отсрочки выплаты основного долга
            if (gracePeriod > 0){
                /**при отсрочки сумма основного долга до истечения срока не меняется*/
                // создаем объект класса
                monthlyPayment = MonthlyPayment(i, 0.0f,
                    interestAmountInMonth, interestAmountInMonth, amount)
                //добавляем объект в массив
                monthlyPaymentList.add(monthlyPayment)
                //уменьшаем значение льготного периода
                gracePeriod--
            } else{
                //вычисляем основную сумму выплаты
                totalAmountInMonth = mainDebtInMonth + interestAmountInMonth
                //уменьшаем сумму основного долга
                amount -= mainDebtInMonth
                //убираем мелкую погрешность в последней строке
                if (i == loan.period)
                    amount = 0.0f

                //создаем объект класса
                monthlyPayment = MonthlyPayment(i, mainDebtInMonth,
                    interestAmountInMonth, totalAmountInMonth, amount)
                //добавляем объект в массив
                monthlyPaymentList.add(monthlyPayment)
            }
        }
        //calculate the total payment amount
        val amountFinal: Float = loan.amount + interestAmountOverall
        val interestNet: Int = ((amountFinal / loan.amount - 1) * 100f).toInt()

        val loanResult = LoanResult(
            isAnnuityType = true,
            amountInitial = loan.amount.toFloat(),
            amountFinal = amountFinal,
            period = loan.period,
            interestGross = loan.interest,
            interestNet = interestNet,
            monthlyPayments = monthlyPaymentList
        )
        _loanResult.value = loanResult
    }
}