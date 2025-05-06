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
import com.example.vladosposos.helpers.SharedPreferencesHelper
import com.example.vladosposos.helpers.SupabaseHelper
import com.example.vladosposos.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
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
        setGradientText(binding.gradientCreateAccTV)
        setGradientText(binding.gradientForgotPassword)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        applyClick()
    }

    private fun applyClick() {
        with(binding) {
            gradientCreateAccTV.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment) }
            logInBttn.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    launchCoroutine(enterEmailET.text.toString(), enterPasswordEditText.text.toString())
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

    private suspend fun launchCoroutine(userEmail: String, userPassword: String) {
        with(binding) {
            if(userEmail == "") errorEnterEmailTV.text = getString(R.string.the_field_is_required)
            else {
                if(!isValidEmail(userEmail)) errorEnterEmailTV.text = getString(R.string.incorrect_email_format)
                else{
                    if(!supabaseHelper.isUserExist(userEmail)) errorEnterEmailTV.text = getString(R.string.user_not_found)
                    else {
                        errorEnterEmailTV.text = ""
                        if(userPassword.count() < 6) {
                            errorEnterPasswordTV.text = getString(R.string.password_must_contain_more_than_5_characters)
                        } else {
                            try {
                                errorEnterPasswordTV.text = ""
                                supabaseHelper.signInWithEmail(userEmail, userPassword)
                                if(materialCheckBox.isChecked) sharedPreferencesHelper.rememberMe()
                                else sharedPreferencesHelper.notRememberMe()
                                findNavController().navigate(R.id.action_loginFragment_to_tiVoshelFragment)
                            }
                            catch (e: Exception) {
                                errorEnterPasswordTV.text = getString(R.string.invalid_password)
                            }
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