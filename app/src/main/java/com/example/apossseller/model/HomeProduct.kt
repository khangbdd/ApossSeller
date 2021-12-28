package com.example.apossseller.model

import java.text.DecimalFormat

data class HomeProduct(
    val id: Long,
    val image: Image,
    val name: String,
    val price: Int,
    val rating: Float,
    val purchased: Int
){
    fun priceToString(): String{
        val formatter = DecimalFormat("#,###")
        val formattedNumber: String = formatter.format(price)
        return "$formattedNumber VNĐ"
    }
}