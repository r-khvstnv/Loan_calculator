package com.rssll971.loancalculator.ui.fragments.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.databinding.FragmentCreditCalculationBinding
import com.rssll971.loancalculator.di.component.DaggerFragmentComponent
import com.rssll971.loancalculator.di.module.FragmentModule
import javax.inject.Inject


class CreditCalculationFragment : Fragment(), CalculationContract.CalculatorView {
    private var _binding: FragmentCreditCalculationBinding? = null
    private val binding get() = _binding!!
    private var isAnnuity = true

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
        _binding = FragmentCreditCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.getCreditType(isAnnuity)
        showCreditType()

    }

    private fun injector(){
        val fragmentComponent = DaggerFragmentComponent.builder().fragmentModule(FragmentModule()).build()
        fragmentComponent.inject(this)
    }

    private fun showCreditType(){
        val creditType = if (isAnnuity)
                getString(R.string.st_annuity)
            else
                getString(R.string.st_proportional)
        binding.tvCreditType.text = creditType
    }

}