package com.rssll971.loancalculator.ui.fragments.initial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme
import com.rssll971.loancalculator.utils.Constants

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
        findNavController().navigate("${Constants.DEST_CALCULATION}/$isAnnuityType")
    }
}





