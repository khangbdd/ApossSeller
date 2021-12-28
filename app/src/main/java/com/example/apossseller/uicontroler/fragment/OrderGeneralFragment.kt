package com.example.apossseller.uicontroler.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentOrderGeneralBinding
import com.example.apossseller.uicontroler.activity.OrderActivity
import com.example.apossseller.viewmodel.OrderGeneralViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderGeneralFragment : Fragment() {

    private val viewModel: OrderGeneralViewModel by viewModels()
    lateinit var binding: FragmentOrderGeneralBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_general, container, false)
        binding.lifecycleOwner = this
        binding.viewModel =viewModel
        binding.lnManageOrder.setOnClickListener {
            val intent = Intent(this.context, OrderActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadTotal()
    }
}