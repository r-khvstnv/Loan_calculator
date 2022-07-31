package com.rssll971.loancalculator.ui.fragments.initial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.ui.fragments.calculation.LoanCalculationFragment
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

class InitialFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoanCalcTheme {
                    InitialUi {
                        isAnnuity ->
                        requestLoanCalculatorByType(isAnnuityType = isAnnuity)
                    }
                }
            }
        }
    }


    private fun requestLoanCalculatorByType(isAnnuityType: Boolean){
        val bundle = Bundle()
        bundle.putBoolean(getString(R.string.title_credit_type), isAnnuityType)
        val calculationFragment = LoanCalculationFragment()
        calculationFragment.arguments = bundle
        parentFragmentManager.beginTransaction().apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.ll_container, calculationFragment)
            setReorderingAllowed(true)
            addToBackStack("initial")
            commit()
        }
    }
}





