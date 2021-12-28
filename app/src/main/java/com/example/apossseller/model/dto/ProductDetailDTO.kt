package com.example.apossseller.model.dto

data class ProductDetailDTO(
    val description: String,
    val kindId: Long,
    val kindName: String,
    val name: String,
    val price: Int,
    val purchase: Int,
    val quantity: Int,
    val rating: Float,
    val totalReview: Int
)
