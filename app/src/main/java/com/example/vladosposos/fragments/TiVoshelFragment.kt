package com.example.vladosposos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vladosposos.R
import com.example.vladosposos.databinding.FragmentTiVoshelBinding


class TiVoshelFragment : Fragment() {

    private val binding: FragmentTiVoshelBinding by lazy { FragmentTiVoshelBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}