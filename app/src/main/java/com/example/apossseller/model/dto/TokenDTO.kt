package com.example.apossseller.model.dto

data class TokenDTO(
    var accessToken: String,
    var tokenType: String,
    var refreshToken: String,
){
    fun getFullAccessToken(): String{
        return "$tokenType Seller $accessToken"
    }
}