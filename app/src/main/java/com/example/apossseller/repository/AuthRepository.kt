package com.example.apossseller.repository

import com.example.apossseller.repository.webservice.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    val authService: AuthService by lazy {
        RetrofitInstance.retrofit.create(AuthService::class.java)
    }

    suspend fun getAccessToken(refreshToken: String): Response<String> {
        return authService.getAccessToken(refreshToken)
    }
}