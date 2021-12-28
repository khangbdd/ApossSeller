package com.example.apossseller.repository

import com.example.apossseller.model.dto.ProductResponseDTO
import com.example.apossseller.repository.webservice.ProductAPIService
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(){
    private val productService: ProductAPIService by lazy {
        RetrofitInstance.retrofit.create(ProductAPIService::class.java)
    }
    suspend fun loadProductByKeywordAndSort(pageNo: Int = 1, keyword: String = "", sortBy: String = "id", sortDir: String = "asc",): Response<ProductResponseDTO>{
        return productService.getProductsByKeyword(keyword, pageNo, sortBy, sortDir)
    }
}