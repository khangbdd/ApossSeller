package com.example.apossseller.repository.webservice

import com.example.apossseller.model.dto.OrderDTO
import com.example.apossseller.model.dto.OrderItemDTO
import com.example.apossseller.utils.OrderStatus
import retrofit2.Response
import retrofit2.http.*

interface OrderService {

    @POST("order")
    suspend fun addNewOrder(
        @Body orderDTO: OrderDTO,
        @Header("Authorization") token: String,
    ): Response<String>

    @PUT("order/hold")
    suspend fun holdProduct(
        @Body listOrderItemDTO: List<OrderItemDTO>,
        @Header("Authorization") token: String,
    ): Response<String>

    @PUT("order/reduceHold")
    suspend fun reduceHold(
        @Body listOrderItemDTO: List<OrderItemDTO>,
        @Header("Authorization") token: String,
    ): Response<String>

    @POST("order/all-order-by")
    suspend fun getAllOrderByStatus(
        @Body orderStatus: OrderStatus,
        @Header("Authorization") token: String,
    ): Response<List<OrderDTO>>

    @POST("order/order-by-id/{id}")
    suspend fun getOrderById(
        @Header("Authorization") accessToken: String,
        @Path (value = "id") id: Long
    ): Response<OrderDTO>

    @POST("order/cancel-order-customer/{id}")
    suspend fun cancelOrder(
        @Path(value = "id") id: Long,
        @Body cancelReason: String,
        @Header("Authorization") accessToken: String,
    ): Response<String>

    @POST("order/change-order-status/{id}")
    suspend fun changeOrderStatus(
        @Path(value = "id") id: Long,
        @Body orderStatus: OrderStatus,
        @Header("Authorization") accessToken: String,
    ): Response<String>
}