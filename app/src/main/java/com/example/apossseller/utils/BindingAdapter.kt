package com.example.apossseller.utils

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.apossseller.R
import com.example.apossseller.model.*
import com.example.apossseller.uicontroler.adapter.*
import me.relex.circleindicator.CircleIndicator3

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
@BindingAdapter( "indicatorSize")
fun bindIndicatorSize(indicator: CircleIndicator3, size: Int){
    indicator.createIndicators(size , 0)
}
@BindingAdapter("imagesData")
fun bindDetailProductImageViewPager(viewPager2: ViewPager2, data: List<Image>?){
    val adapter = viewPager2.adapter as DetailProductImageViewPagerAdapter
    adapter.submitList(data)
}
@BindingAdapter("stringProperty")
fun bindStringProperty(recyclerView: RecyclerView, data: List<ProductDetailProperty>?){
    val adapter = recyclerView.adapter as StringPropertyAdapter
    adapter.submitList(data)
}
@BindingAdapter("colorProperty")
fun bindColorProperty(recyclerView: RecyclerView, data: List<ProductDetailProperty>?){
    val adapter = recyclerView.adapter as ColorPropertyAdapter
    adapter.submitList(data)
}
@BindingAdapter("stringPropertyValue")
fun bindStringPropertyValue(recyclerView: RecyclerView, data: List<PropertyValue>?){
    val adapter = recyclerView.adapter as StringDetailPropertyAdapter
    adapter.submitList(data)
}
@BindingAdapter("colorPropertyValue")
fun bindColorPropertyValue(recyclerView: RecyclerView, data: List<PropertyValue>?){
    val adapter = recyclerView.adapter as ColorDetailPropertyAdapter
    adapter.submitList(data)
}
@BindingAdapter("setToggleColor")
fun bindColorToImageBackground(toggleButton: ToggleButton, data: String?){
    val myColor: Int = Color.parseColor(data)
    toggleButton.setBackgroundColor(myColor)
}