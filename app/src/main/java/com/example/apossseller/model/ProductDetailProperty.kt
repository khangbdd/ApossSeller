package com.example.apossseller.model

data class ProductDetailProperty(
    val id: Long,
    val name: String,
    val values: List<PropertyValue>,
    var valueCountSummarize: Int,
)
