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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apossseller.R
import com.example.apossseller.databinding.FragmentDetailOrderBinding
import com.example.apossseller.uicontroler.adapter.BillingItemsAdapter
import com.example.apossseller.uicontroler.adapter.OrderDeliveringStateAdapter
import com.example.apossseller.utils.OrderStatus
import com.example.apossseller.viewmodel.OrderDetailViewModel
import com.example.apossseller.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderFragment : Fragment(), SelectStatusDialog.SelectStatusInterface {

    private lateinit var binding: FragmentDetailOrderBinding
    private val viewModelOrders: OrderViewModel by activityViewModels()
    private val viewModel: OrderDetailViewModel by viewModels()
    private lateinit var orderDetailBillingItem: BillingItemsAdapter

    private val args: DetailOrderFragmentArgs by navArgs()

    private lateinit var orderDeliveringStateAdapter: OrderDeliveringStateAdapter
    lateinit var selectStatusDialog: SelectStatusDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail_order, container, false )
        if(viewModelOrders.currentOrder != null){
            viewModel.setCurrentOrder(viewModelOrders.currentOrder!!)
        }else{
            if(args.id != -1L){
                viewModel.setDetailOrderById(args.id)
            }else{
                Toast.makeText(this.context, "Unknown error", Toast.LENGTH_SHORT).show()
            }
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        orderDetailBillingItem = BillingItemsAdapter()
        binding.billingItems.adapter = orderDetailBillingItem
        orderDeliveringStateAdapter = OrderDeliveringStateAdapter()
        binding.deliveringState.adapter = orderDeliveringStateAdapter
        selectStatusDialog = SelectStatusDialog(requireActivity(), this, viewModelOrders.currentOrder!!.status.toString())
        setShowButton()
        setBackPress()
        setCancelOrder()
        setUpdateClick()
        return binding.root
    }

    private fun setBackPress(){
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    private fun setCancelOrder(){
        binding.cancel.setOnClickListener {
            findNavController().navigate(DetailOrderFragmentDirections.actionDetailOrderFragmentToCancelOrderFragment(viewModel.detailOrder.value!!.id))
        }
    }

    private fun setUpdateClick()
    {
        binding.update.setOnClickListener {
            selectStatusDialog.startLoading()
        }
    }


    private fun setShowButton(){
//        if(viewModel.detailOrder.value!!.status != OrderStatus.Success){
//            binding.update.visibility = View.VISIBLE
//        }else{
//            binding.update.visibility = View.GONE
//        }
        if(viewModel.detailOrder.value!!.status == OrderStatus.Pending || viewModel.detailOrder.value!!.status == OrderStatus.Confirmed){
            binding.cancel.visibility = View.VISIBLE
                //binding.editAddress.visibility = View.VISIBLE
        }else{
            binding.cancel.visibility = View.GONE
            //binding.editAddress.visibility = View.GONE
        }

    }

    override fun onSaveClick(status: String) {
        selectStatusDialog.dismissDialog()
        if (status == "Pending")
        {
            viewModel.changeStatus(OrderStatus.Pending, viewModelOrders.currentOrder!!.id);
            formerLoad(viewModelOrders.currentOrder!!.getStatusString())
        } else if (status == "Confirmed")
        {
            viewModel.changeStatus(OrderStatus.Confirmed, viewModelOrders.currentOrder!!.id);
            formerLoad(viewModelOrders.currentOrder!!.getStatusString())
        } else if (status == "Delivering")
        {
            viewModel.changeStatus(OrderStatus.Delivering, viewModelOrders.currentOrder!!.id);
            formerLoad(viewModelOrders.currentOrder!!.getStatusString())
        }else if (status == "Cancel")
        {
            binding.cancel.setOnClickListener {
                findNavController().navigate(DetailOrderFragmentDirections.actionDetailOrderFragmentToCancelOrderFragment(viewModel.detailOrder.value!!.id))
            }
        }
    }

    fun formerLoad(status: String)
    {
        if (status == "Pending")
        {
            viewModelOrders.loadPendingOrder()
        } else if (status == "Confirmed")
        {
            viewModelOrders.loadConfirmedOrder()
        } else if (status == "Delivering") {
            viewModelOrders.loadDeliveringOrder()
        }
    }


}