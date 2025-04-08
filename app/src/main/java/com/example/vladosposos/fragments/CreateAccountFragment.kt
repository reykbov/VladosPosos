package com.example.vladosposos.fragments

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.vladosposos.R
import com.example.vladosposos.databinding.FragmentCreateAccountBinding
import androidx.core.graphics.toColorInt

class CreateAccountFragment : Fragment() {
    private val binding: FragmentCreateAccountBinding by lazy { FragmentCreateAccountBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyClick()
        setGradientText(binding.gradientLoginTV)
    }

    private fun applyClick() {
        with(binding) {
            logInBttn.setOnClickListener { findNavController().navigate(R.id.onboardingFragment2) }
            gradientLoginTV.setOnClickListener { findNavController().navigate(R.id.loginFragment) }
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
}