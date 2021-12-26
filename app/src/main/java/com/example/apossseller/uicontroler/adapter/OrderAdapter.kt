package com.example.apossseller.uicontroler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apossseller.databinding.ItemOrderBinding
import com.example.apossseller.model.Order
import com.example.apossseller.uicontroler.adapter.BillingItemsAdapter
import com.example.apossseller.utils.OrderStatus

class OrderAdapter(
    private val onClickListener: OnClickListener,
    private val orderInterface: OrderInterface
) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(DiffCallBack) {

    open class OnClickListener(val clickListener: (order: Order) -> Unit) {
        fun onClick(order: Order) = clickListener(order)
    }

    interface OrderInterface {
        fun onConfirmClick(id:Long)
    }

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order = order
            val adapter = BillingItemsAdapter()
            binding.billingItems.adapter = adapter
            setUpRatingButton(order)
        }


        private fun setUpRatingButton(order: Order) {
            if (order.status == OrderStatus.Pending) {
                binding.confirm.visibility = View.VISIBLE
            } else {
                binding.confirm.visibility = View.GONE
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = getItem(position)
        holder.bind(currentOrder)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currentOrder)
        }
        holder.binding.confirm.setOnClickListener {
            orderInterface.onConfirmClick(currentOrder.id)
        }
    }
}