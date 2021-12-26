package com.example.apossseller.repository

import com.example.apossseller.repository.webservice.OrderService
import com.squareup.moshi.JsonClass
import javax.inject.Inject

class OrderRepository @Inject constructor() {

    val orderService: OrderService by lazy {
        RetrofitInstance.retrofit.create(OrderService::class.java)
    }
}