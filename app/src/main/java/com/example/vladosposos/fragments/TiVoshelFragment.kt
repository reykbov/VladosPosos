package com.example.vladosposos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vladosposos.R
import com.example.vladosposos.helpers.SharedPreferencesHelper
import com.example.vladosposos.helpers.SupabaseHelper
import com.example.vladosposos.databinding.FragmentTiVoshelBinding
import kotlinx.coroutines.launch


class TiVoshelFragment : Fragment() {
    private val binding: FragmentTiVoshelBinding by lazy { FragmentTiVoshelBinding.inflate(layoutInflater) }
    private val supabaseHelper = SupabaseHelper()
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
        with(binding) {
            buttonExit.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    launchCoroutine()
                }
            }
        }
    }

    private suspend fun launchCoroutine() {
        supabaseHelper.userSignOut()
        sharedPreferencesHelper.notRememberMe()
        findNavController().navigate(R.id.action_tiVoshelFragment_to_loginFragment)
    }

}