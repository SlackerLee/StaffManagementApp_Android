package com.example.myapplication.models.enum.error

sealed class LoginError(message: String) : Exception(message) {
    class NetworkError(description: String) : LoginError(description)
    class DecodingError(description: String) : LoginError(description)
    class ApiError(description: String) : LoginError(description)
}