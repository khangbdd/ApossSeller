package com.example.apossseller.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.apossseller.R
import com.example.apossseller.model.HomeProduct
import com.example.apossseller.model.Order
import com.example.apossseller.model.OrderBillingItem
import com.example.apossseller.model.OrderDeliveringState
import com.example.apossseller.uicontroler.adapter.BillingItemsAdapter
import com.example.apossseller.uicontroler.adapter.HomeProductAdapter
import com.example.apossseller.uicontroler.adapter.OrderAdapter
import com.example.apossseller.uicontroler.adapter.OrderDeliveringStateAdapter

@BindingAdapter("image")
fun bindImage(imageView: ImageView, image: Uri?) {
    Glide.with(imageView.context)
        .load(image)
        .apply(
            RequestOptions().placeholder(R.drawable.animation_loading)
        )
        .into(imageView)
    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
}
@BindingAdapter("billingItemData")
fun bindBillingItemRecyclerView(recyclerView: RecyclerView, data: List<OrderBillingItem>?){
    val adapter = recyclerView.adapter as BillingItemsAdapter
    adapter.submitList(data)
}
@BindingAdapter ("orderData")
fun bindOrderRecyclerView(recyclerView: RecyclerView, data: List<Order>?){
    val adapter = recyclerView.adapter as OrderAdapter
    adapter.submitList(data)
}
@BindingAdapter("productData")
fun bindProductRecyclerView(recyclerView: RecyclerView, data: List<HomeProduct>?){
    val adapter = recyclerView.adapter as HomeProductAdapter
    adapter.submitList(data)
}
@BindingAdapter("statusIconData")
fun bindStatusIcon(imageView: ImageView, data: OrderStatus?){
    if(data!=null){
        when(data){
            OrderStatus.Success ->{
                imageView.setImageResource(R.drawable.ic_order_pass)
            }
            OrderStatus.Pending ->{
                imageView.setImageResource(R.drawable.ic_order_pending)
            }
            OrderStatus.Cancel ->{
                imageView.setImageResource(R.drawable.ic_order_cancel)
            }
            OrderStatus.Confirmed ->{
                imageView.setImageResource(R.drawable.ic_order_confirm)
            }
            OrderStatus.Delivering ->{
                imageView.setImageResource(R.drawable.ic_order_delivering)
            }
        }
    }
}
@BindingAdapter("deliveringStateData")
fun bindDeliveringStateRecyclerView(recyclerView: RecyclerView, data: List<OrderDeliveringState>?){
    val adapter = recyclerView.adapter as OrderDeliveringStateAdapter
    adapter.submitList(data)
}
