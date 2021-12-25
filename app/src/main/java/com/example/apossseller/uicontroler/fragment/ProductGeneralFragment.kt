package com.example.apossseller.uicontroler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentProductGeneralBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductGeneralFragment : Fragment() {

    private lateinit var binding: FragmentProductGeneralBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_general, container, false)
        return inflater.inflate(R.layout.fragment_product_general, container, false)
    }
}