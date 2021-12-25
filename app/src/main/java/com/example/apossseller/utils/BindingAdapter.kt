package com.example.apossseller.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.apossseller.R

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
@BindingAdapter("imagesPath")
fun bindImagePath(imageView: ImageView, image: Uri?)
{
    Glide.with(imageView.context)
        .asBitmap()
        .load(image)
        .apply(
            RequestOptions().placeholder(R.drawable.animation_loading)
        )
//        .error(RequestOptions().placeholder(R.drawable.ic_baseline_error_24))
        .transition(BitmapTransitionOptions.withCrossFade())
        .into(imageView)
    imageView.scaleType= ImageView.ScaleType.CENTER_CROP
}