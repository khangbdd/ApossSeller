package com.example.apossseller.uicontroler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apossseller.databinding.ItemProductStringPropertyBinding
import com.example.apossseller.model.ProductDetailProperty

class StringPropertyAdapter(private val propertySelect: StringDetailPropertyAdapter.PropertyStringValueSelected) :
    ListAdapter<ProductDetailProperty, StringPropertyAdapter.StringPropertyViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<ProductDetailProperty>() {
        override fun areItemsTheSame(
            oldItem: ProductDetailProperty,
            newItem: ProductDetailProperty
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductDetailProperty,
            newItem: ProductDetailProperty
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class StringPropertyViewHolder(
        private val binding: ItemProductStringPropertyBinding,
        private val propertySelect: StringDetailPropertyAdapter.PropertyStringValueSelected
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentProperty: ProductDetailProperty) {
            binding.stringProperty.adapter = StringDetailPropertyAdapter(propertySelect)
            binding.property = currentProperty
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringPropertyViewHolder {
        return StringPropertyViewHolder(
            ItemProductStringPropertyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            propertySelect
        )
    }
    override fun onBindViewHolder(holder: StringPropertyViewHolder, position: Int) {
        val currentProperty = getItem(position)
        holder.bind(currentProperty)
    }


}
