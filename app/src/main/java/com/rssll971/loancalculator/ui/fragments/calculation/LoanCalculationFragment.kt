package com.rssll971.loancalculator.ui.fragments.calculation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rssll971.loancalculator.SharedViewModel
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.models.LoanResult
import com.rssll971.loancalculator.ui.fragments.calculation.composable.CalculateButton
import com.rssll971.loancalculator.ui.fragments.calculation.composable.CalculatorTextFields
import com.rssll971.loancalculator.ui.fragments.calculation.composable.CalculatorTopBar
import com.rssll971.loancalculator.ui.fragments.calculation.composable.LoanResultCard
import com.rssll971.loancalculator.ui.theme.LoanCalcTheme
import com.rssll971.loancalculator.utils.Constants


class LoanCalculationFragment : Fragment() {
    private val viewModel: SharedViewModel by viewModels()

    private var isAnnuity = true

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val args = arguments?.getBoolean(Constants.ARGS_IS_ANNUITY)
        args?.let {
            isAnnuity = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoanCalcTheme {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ){
                        CalculatorTopBar(
                            loanType = //TODO CHANGE TO STATE FLOW
                            if (isAnnuity) stringResource(id = R.string.title_annuity)
                            else stringResource(id = R.string.title_differential),
                            onCloseClick = {
                                findNavController().popBackStack()
                            },
                            onSwapLoanTypeClick = {
                                //TODO IMPLEMENT LOAN TYPE FLIPPING
                            }
                        )


                        Spacer(modifier = Modifier.padding(10.dp))
                        CalculatorTextFields(viewModel = viewModel)
                        Spacer(modifier = Modifier.padding(20.dp))


                        CalculateButton {
                            //TODO CALCULATE FUN
                        }

                        Spacer(modifier = Modifier.padding(50.dp))
                        val loanResult = LoanResult( //TODO REMOVE TEST IMPL
                            true,
                            100000000.123f,
                            122000000.456f,
                            48,
                            25,
                            22,
                            emptyList()
                        )

                        LoanResultCard(loanResult = loanResult) {
                            findNavController().navigate(Constants.DEST_DETAILS)
                        }
                        Spacer(modifier = Modifier.padding(30.dp))
                    }
                }
            }
        }
    }
}