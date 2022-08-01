package com.rssll971.loancalculator.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rssll971.loancalculator.ui.fragments.calculation.LoanCalculationViewModel
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme

class DetailsFragment: Fragment() {
    private val viewModel: LoanCalculationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoanCalcTheme {
                    MonthlyPaymentLazyColumnPreview()
                }
            }
        }
    }
}