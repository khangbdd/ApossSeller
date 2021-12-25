package com.example.apossseller.repository.webservice

import com.example.apossseller.model.dto.LoginDTO
import com.example.apossseller.model.dto.TokenDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("seller-sign-in")
    suspend fun login(
        @Body loginDTO: LoginDTO
    ): Response<TokenDTO>
}