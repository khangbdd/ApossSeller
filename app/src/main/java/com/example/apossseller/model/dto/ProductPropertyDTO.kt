package com.example.apossseller.model.dto

data class ProductPropertyDTO(
    val id: Long,
    val name: String,
    val color: Boolean,
    val valueDTOS: List<ProductPropertyValueDTO>
)
