package com.example.myapplication.core

object NetworkConstant {
    const val DOMAIN_URL = "https://reqres.in/api/"
    const val LOGIN_URL = "${DOMAIN_URL}login?delay=1"
    const val STAFF_LIST_URL = "${DOMAIN_URL}users?page="

    // header key
    const val API_KEY_HEADER = "x-api-key"
    const val API_KEY_HEADER_VALUE = "reqres-free-v1"
}
