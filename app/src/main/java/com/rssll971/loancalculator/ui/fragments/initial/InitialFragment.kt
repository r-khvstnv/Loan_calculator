package com.rssll971.loancalculator.ui.fragments.initial

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import android.graphics.Color
import com.rssll971.loancalculator.R
import com.rssll971.loancalculator.databinding.FragmentInitialBinding
import com.rssll971.loancalculator.di.component.DaggerFragmentComponent
import com.rssll971.loancalculator.di.module.FragmentModule
import com.rssll971.loancalculator.ui.fragments.calculation.CreditCalculationFragment
import javax.inject.Inject


class InitialFragment : Fragment(), InitialContract.InitialView {
    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: InitialContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        checkNightModeStatus()

        with(binding){
            llAnnuity.setOnClickListener {
                presenter.creditTypeWasClicked(true)
            }
            llProportional.setOnClickListener {
                presenter.creditTypeWasClicked(false)
            }
            ibSettings.setOnClickListener {
                presenter.infoWasClicked()
            }
            tbNightMode.setOnCheckedChangeListener { _, isChecked ->
                presenter.switchNightMode(isChecked = isChecked)
            }
        }
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    private fun injector(){
        val fragmentComponent =
            DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule()).build()
        fragmentComponent.inject(this)
    }

    private fun checkNightModeStatus(){
        binding.tbNightMode.isChecked = when(resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK){
            Configuration.UI_MODE_NIGHT_NO -> {
                false
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                true
            }
            else -> false
        }
    }

    override fun requestCreditCalculationFragment(isAnnuity: Boolean) {
        val bundle = Bundle()
        bundle.putBoolean(getString(R.string.st_credit_type), isAnnuity)
        val calculationFragment = CreditCalculationFragment()
        calculationFragment.arguments = bundle
        parentFragmentManager.beginTransaction().apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.ll_container, calculationFragment)
            setReorderingAllowed(true)
            addToBackStack("initial")
            commit()
        }
    }

    override fun showInfo(){
        val dialogSettings = Dialog(requireContext())
        dialogSettings.setContentView(R.layout.dialog_settings)

        dialogSettings.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogSettings.show()
    }
}