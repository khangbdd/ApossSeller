package com.example.apossseller.uicontroler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apossseller.databinding.ItemProductBinding
import com.example.apossseller.model.HomeProduct

class HomeProductAdapter(
    private val onClickListener: OnClickListener
) :
    ListAdapter<HomeProduct, HomeProductAdapter.HomeProductViewHolder>(DiffCallBack) {

    open class OnClickListener(val clickListener: (id: Long) -> Unit) {
        fun onClick(id: Long) = clickListener(id)
    }

    object DiffCallBack : DiffUtil.ItemCallback<HomeProduct>() {
        override fun areItemsTheSame(oldItem: HomeProduct, newItem: HomeProduct): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HomeProduct, newItem: HomeProduct): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class HomeProductViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: HomeProduct) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductViewHolder {
        return HomeProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        val currentProduct = getItem(position)
        holder.bind(currentProduct)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currentProduct.id)
        }
    }
}