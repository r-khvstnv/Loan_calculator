package com.rssll971.loancalculator.ui.fragments.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.rssll971.loancalculator.MonthResultAdapter
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.databinding.FragmentCreditCalculationBinding
import com.rssll971.loancalculator.di.component.DaggerFragmentComponent
import com.rssll971.loancalculator.di.module.FragmentModule
import com.rssll971.loancalculator.models.MonthCreditModel
import javax.inject.Inject


class CreditCalculationFragment : Fragment(), CalculationContract.CalculatorView {
    private var _binding: FragmentCreditCalculationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mSmartBannerAd: AdView
    private var isAnnuity = true
    private var rvHeight: Int = 300

    @Inject
    lateinit var presenter: CalculationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isAnnuity = this.requireArguments().getBoolean(getString(R.string.st_credit_type))
        _binding =
            FragmentCreditCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.getCreditType(isAnnuity)

        //prepare Ads
        MobileAds.initialize(requireContext()) {}
        mSmartBannerAd = activity?.findViewById(R.id.adView_result)!!
        val adRequest = AdRequest.Builder().build()
        mSmartBannerAd.loadAd(adRequest)
        mSmartBannerAd.adListener = object : AdListener(){
            override fun onAdClosed() {
                mSmartBannerAd.loadAd(adRequest)
            }
        }

        with(binding){
            llCalculate.setOnClickListener {
                presenter.validateCreditData(
                    etAmount.text.toString(),
                    etInterest.text.toString(),
                    etLoanPeriod.text.toString(),
                    etGracePeriod.text.toString())
            }
        }
    }

    private fun injector(){
        val fragmentComponent =
            DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule()).build()
        fragmentComponent.inject(this)
    }

    override fun showCreditType(isAnnuity: Boolean) {
        val creditType = if (isAnnuity)
                getString(R.string.st_annuity)
            else
                getString(R.string.st_proportional)
        binding.tvCreditType.text = creditType
    }

    override fun showPoorDataErrorMessage() {
        val snackBar = Snackbar.make(
            activity?.findViewById(android.R.id.content)!!,
            getString(R.string.st_add_data),
            Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.colorSnackBarBackground))
        snackBar.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.colorTextAccent))
        snackBar.show()
    }

    override fun onEstimationCallback(
        monthCreditList: ArrayList<MonthCreditModel>,
        resultData: MonthCreditModel
    ) {
        setupResultRecyclerView(monthCreditList = monthCreditList)
        //show overall result
        with(binding){
            tvTotalMainMonthAmount.text = String.format("%,.2f", resultData.mainMonthDebt)
            tvTotalInterestInMonth.text = String.format("%,.2f", resultData.interestInMonth)
            tvTotalOverallAmountInMonth.text = String.format("%,.2f", resultData.overallAmountInMonth)
            llCreditResult.visibility = View.VISIBLE
            scrollView.requestChildFocus(adViewResult, adViewResult)
        }
    }

    override fun provideRecyclerViewHeight(height: Int) {
        rvHeight = height
    }
    private fun setupResultRecyclerView(monthCreditList: ArrayList<MonthCreditModel>){
        val resultAdapter = MonthResultAdapter(monthCreditList, requireContext())
        with(binding){
            rvDetailsContainer.layoutManager =
                LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)
            rvDetailsContainer.layoutParams.height = rvHeight
            rvDetailsContainer.adapter = resultAdapter
        }

    }
}