package com.example.apossseller.model.dto

data class ProductPropertyValueDTO(
    val id: Long,
    val name: String,
    val value: String,
    val quantity: Int,
    val additionalPrice: Int
)