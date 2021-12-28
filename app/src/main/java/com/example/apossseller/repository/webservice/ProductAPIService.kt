package com.example.apossseller.repository.webservice

import com.example.apossseller.model.dto.ProductResponseDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductAPIService {

    @GET("products/search")
    suspend fun getProductsByKeyword(
        @Query("keyword") keyword: String = "",
        @Query("pageNo") pageNo: Int = 1,
        @Query("sortBy") sortBy: String = "id",
        @Query("sortDir") sortDir: String = "asc",
    ): Response<ProductResponseDTO>

//    @GET("products/{id}")
//    suspend fun getProductById(
//        @Path(value = "id") id: Long
//    ): Response<ProductDetailDTO>
//
//    @GET("products/{id}/images")
//    suspend fun getProductImagesById(
//        @Path(value = "id") id: Long
//    ): Response<List<ProductImageDTO>>
//
//    @GET("products/kind/{id}")
//    suspend fun getProductByKindId(
//        @Path(value = "id") id: Long
//    ): Response<ProductResponseDTO>
//
//    @GET("products/{id}/properties")
//    suspend fun getProductPropertiesById(
//        @Path(value = "id") id: Long,
//        @Query("isColor") isColor: Boolean,
//    ): Response<List<ProductPropertyDTO>>
//
//    @GET("products/{id}/ratings")
//    suspend fun getProductRatingsById(
//        @Path(value = "id") id: Long,
//    ): Response<List<ProductRatingDTO>>
}