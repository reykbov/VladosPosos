package com.example.vladosposos.fragments

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vladosposos.R
import com.example.vladosposos.SharedPreferencesHelper
import com.example.vladosposos.SupabaseHelper
import com.example.vladosposos.databinding.FragmentCreateAccountBinding
import kotlinx.coroutines.launch

class CreateAccountFragment : Fragment() {
    private val binding: FragmentCreateAccountBinding by lazy { FragmentCreateAccountBinding.inflate(layoutInflater) }
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
        setGradientText(binding.gradientLoginTV)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        applyClick()
    }

    private fun applyClick() {
        with(binding) {
            gradientLoginTV.setOnClickListener { findNavController().navigate(R.id.loginFragment) }
            logInBttn.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    launchCoroutine(enterEmailET.text.toString(), enterPasswordEditText.text.toString(), confirmPasswordEditText.text.toString())
                }
            }
        }
    }

    private fun setGradientText(textView: TextView) {
        val paint = textView.paint
        val width = paint.measureText(textView.text.toString())
        textView.paint.shader = LinearGradient(
            0f, 0f, width, textView.textSize,
                intArrayOf(
                    getColor(R.color.gradient_start_color),
                    getColor(R.color.gradient_center_color),
                    getColor(R.color.gradient_end_color)
                ),
            null,
            Shader.TileMode.CLAMP
        )
    }

    private fun getColor(color: Int): Int {
        return ContextCompat.getColor(requireContext(), color)
    }

    private suspend fun launchCoroutine(userEmail: String, userPassword: String, userConfirmedPassword: String) {
        var flag1: Boolean
        var flag2: Boolean
        with(binding) {
            if(userEmail == "") errorEnterEmailTV.text = getString(R.string.the_field_is_required)
            else {
                if(!isValidEmail(userEmail)) errorEnterEmailTV.text = getString(R.string.incorrect_email_format)
                else{
                    if(supabaseHelper.isUserExist(userEmail)) errorEnterEmailTV.text = getString(R.string.email_is_already_registered)
                    else {
                        errorEnterEmailTV.text = ""
                        if(userPassword.count() < 6) {
                            errorEnterPasswordTV.text = getString(R.string.password_must_contain_more_than_5_characters)
                            flag1 = false
                        } else {
                            flag1 = true
                            errorEnterPasswordTV.text = ""
                        }
                        if(userPassword != userConfirmedPassword) {
                            errorConfirmPasswordTV.text = getString(R.string.passwords_do_not_match)
                            flag2 = false
                        } else {
                            flag2 = true
                            errorConfirmPasswordTV.text = ""
                        }
                        if(flag1 && flag2) {
                            supabaseHelper.signUpWithEmail(userEmail, userPassword)
                            if(materialCheckBox.isChecked) sharedPreferencesHelper.rememberMe()
                            else sharedPreferencesHelper.notRememberMe()
                            findNavController().navigate(R.id.tiVoshelFragment)
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(mail: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return mail.matches(emailPattern)
    }
}