package com.example.apossseller.model.dto

data class ProductResponseDTO(
    val content: List<ProductDTO>,
    val last: Boolean,
    val pageNo: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)
