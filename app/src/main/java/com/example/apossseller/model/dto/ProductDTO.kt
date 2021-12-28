package com.example.apossseller.model.dto

data class ProductDTO (
    val favorite: Boolean,
    val id: Long,
    val image: String,
    val name: String,
    val price: Int,
    val rating: Double,
    val purchased: Int,
)
