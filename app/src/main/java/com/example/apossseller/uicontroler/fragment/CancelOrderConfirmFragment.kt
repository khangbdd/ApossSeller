package com.example.apossseller.uicontroler.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentCancelOrderConfirmBinding
import com.example.apossseller.uicontroler.activity.MainActivity

class CancelOrderConfirmFragment : Fragment() {

    private lateinit var binding: FragmentCancelOrderConfirmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cancel_order_confirm, container, false)
        setOnBackPress()
        setContinueButton()
        return binding.root
    }
    private fun setOnBackPress(){
        binding.back.setOnClickListener {
            findNavController().navigate(CancelOrderConfirmFragmentDirections.actionCancelOrderConfirmFragmentToOrderFragment())
        }
    }
    private fun setContinueButton(){
        binding.continueShopping.setOnClickListener {
            findNavController().navigate(CancelOrderConfirmFragmentDirections.actionCancelOrderConfirmFragmentToOrderFragment())
        }
    }

}