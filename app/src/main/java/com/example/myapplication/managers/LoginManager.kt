package com.example.myapplication.managers

import com.example.myapplication.core.NetworkConstant
import com.example.myapplication.models.data.LoginRespData
import com.example.myapplication.models.enum.error.LoginError

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object LoginManager {
    fun login(
        email: String,
        password: String,
        completion: (Result<LoginRespData>) -> Unit
    ) {
        thread {
            try {
                val url = URL(NetworkConstant.LOGIN_URL)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty(NetworkConstant.API_KEY_HEADER, NetworkConstant.API_KEY_HEADER_VALUE)
                conn.doOutput = true

                // Body
                val body = JSONObject().apply {
                    put("email", email)
                    put("password", password)
                }.toString()

                OutputStreamWriter(conn.outputStream).use { writer ->
                    writer.write(body)
                }

                val responseCode = conn.responseCode
                val reader = if (responseCode in 200..299) {
                    BufferedReader(InputStreamReader(conn.inputStream))
                } else {
                    BufferedReader(InputStreamReader(conn.errorStream))
                }

                val responseStr = reader.readText()
                reader.close()

                println("Raw JSON response:\n$responseStr")

                val json = JSONObject(responseStr)

                val respData = LoginRespData(
                    token = json.optString("token", null),
                    error = json.optString("error", null)
                )

                if (respData.error != null && respData.error.isNotEmpty()) {
                    completion(Result.failure(LoginError.ApiError(respData.error)))
                } else {
                    completion(Result.success(respData))
                }

            } catch (e: Exception) {
                completion(Result.failure(LoginError.NetworkError("Network error: ${e.localizedMessage}")))
            }
        }
    }
}
