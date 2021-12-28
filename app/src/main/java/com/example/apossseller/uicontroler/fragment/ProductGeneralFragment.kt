package com.example.apossseller.uicontroler.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentProductGeneralBinding
import com.example.apossseller.uicontroler.activity.ProductManagementAcitity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductGeneralFragment : Fragment() {

    private lateinit var binding: FragmentProductGeneralBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_general, container, false)
        binding.manageProduct.setOnClickListener {
            startActivity(Intent(this.context, ProductManagementAcitity::class.java))
        }
        return binding.root
    }
}