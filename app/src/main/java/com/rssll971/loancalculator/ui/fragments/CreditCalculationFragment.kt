package com.rssll971.loancalculator.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rssll971.loancalculator.databinding.FragmentCreditCalculationBinding


class CreditCalculationFragment : Fragment() {
    private var _binding: FragmentCreditCalculationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

}