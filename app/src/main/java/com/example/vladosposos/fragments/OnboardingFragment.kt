package com.example.vladosposos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.vladosposos.R
import com.example.vladosposos.SharedPreferencesHelper
import com.example.vladosposos.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private val binding: FragmentOnboardingBinding by lazy { FragmentOnboardingBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        applyClick()
    }

    private fun applyClick() {
        binding.getStartedButton.setOnClickListener {
            sharedPreferencesHelper.checkOnboarding()
            findNavController().navigate(R.id.createAccountFragment2)
        }
    }
}