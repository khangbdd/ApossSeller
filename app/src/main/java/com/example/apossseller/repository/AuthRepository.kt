package com.example.apossseller.repository

import com.example.apossseller.repository.webservice.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    val authService: AuthService by lazy {
        RetrofitInstance.retrofit.create(AuthService::class.java)
    }
}