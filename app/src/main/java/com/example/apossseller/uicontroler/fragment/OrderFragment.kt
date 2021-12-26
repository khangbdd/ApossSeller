package com.example.apossseller.uicontroler.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentOrderBinding
import com.example.apossseller.uicontroler.activity.MainActivity
import com.example.apossseller.uicontroler.adapter.OrderAdapter
import com.example.apossseller.utils.OrderStatus
import com.example.apossseller.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), OrderAdapter.OrderInterface {

    private lateinit var binding: FragmentOrderBinding

    private val viewModel: OrderViewModel by activityViewModels()

    private lateinit var orderAdapter: OrderAdapter

    private var status: String = "Pending"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_order, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        orderAdapter = OrderAdapter(OrderAdapter.OnClickListener{
            status = it.getStatusString()
            toOrderDetail(it.id)
            viewModel.setCurrentOrder(it)
        }, this)
        binding.orders.adapter = orderAdapter
        setBottomBar()
        onCurrentOrderChange()
        setBackPress()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        formerLoad(status)
    }

    fun formerLoad(status: String)
    {
        if (status == "Pending")
        {
            viewModel.loadPendingOrder()
        } else if (status == "Confirmed")
        {
            viewModel.loadConfirmedOrder()
        } else if (status == "Delivering") {
            viewModel.loadDeliveringOrder()
        }
    }

    private fun setBackPress(){
        binding.back.setOnClickListener {
            startActivity(Intent(this.context, MainActivity::class.java))
        }
    }
    private fun toOrderDetail(orderId: Long){
        findNavController().navigate(OrderFragmentDirections.actionOrderFragmentToDetailOrderFragment(orderId))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onCurrentOrderChange(){
        viewModel.currentListOrder.observe(this.viewLifecycleOwner, {
            orderAdapter.submitList(viewModel.currentListOrder.value)
            binding.orders.adapter!!.notifyDataSetChanged()
            if(viewModel.currentListOrder.value!!.isEmpty()){
                binding.emptyOrder.visibility = View.VISIBLE
            }else{
                binding.emptyOrder.visibility = View.GONE
            }
        })
    }

    private fun setBottomBar(){
        binding.bottomBar.setOnItemSelectedListener {
            when(it.title.toString()){
                "Pending" ->{
                    viewModel.loadPendingOrder()
                }
                "Confirmed" ->{
                    viewModel.loadConfirmedOrder()
                }
                "Delivering" ->{
                    viewModel.loadDeliveringOrder()
                }
                "Success" ->{
                    viewModel.loadSuccessOrder()
                }
                "Cancel"->{
                    viewModel.loadCancelOrder()
                }
            }

            return@setOnItemSelectedListener true
        }
    }

    override fun onConfirmClick(id: Long) {
        viewModel.changeStatus(OrderStatus.Confirmed, id);
    }
}