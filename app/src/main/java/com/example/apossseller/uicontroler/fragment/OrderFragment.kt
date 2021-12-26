package com.example.apossseller.uicontroler.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.aposs_buyer.uicontroler.fragment.OrderFragmentDirections
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentOrderBinding
import com.example.apossseller.uicontroler.adapter.OrderAdapter
import com.example.apossseller.utils.OrderStatus
import com.example.apossseller.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), OrderAdapter.OrderInterface {

    private lateinit var binding: FragmentOrderBinding

    private val viewModel: OrderViewModel by activityViewModels()

    private lateinit var orderAdapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_order, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        orderAdapter = OrderAdapter(OrderAdapter.OnClickListener{
            toOrderDetail(it.id)
            viewModel.setCurrentOrder(it)
        }, this)
        binding.orders.adapter = orderAdapter
        setBottomBar()
        onCurrentOrderChange()
        setBackPress()
        return binding.root
    }


    private fun setBackPress(){
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
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