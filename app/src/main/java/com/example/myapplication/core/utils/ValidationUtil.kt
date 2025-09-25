package com.example.myapplication.core.utils

object ValidationUtil {
    fun isValidEmail(email: String): Boolean {
        val emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        return Regex(emailRegEx).matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegEx = "^[A-Za-z0-9]{6,10}$"
        return Regex(passwordRegEx).matches(password)
    }
}
