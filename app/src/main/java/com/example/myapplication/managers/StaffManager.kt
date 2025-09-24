package com.example.myapplication.managers

import com.example.myapplication.core.NetworkConstant
import com.example.myapplication.models.data.StaffData
import com.example.myapplication.models.network.staff.StaffListRespData

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object StaffManager {

    fun getStaffList(
        page: Int,
        perPage: Int = 10,
        completion: (Result<StaffListRespData>) -> Unit
    ) {
        thread {
            try {
                val url = URL("${NetworkConstant.STAFF_LIST_URL}$page&per_page=$perPage")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty(NetworkConstant.API_KEY_HEADER, NetworkConstant.API_KEY_HEADER_VALUE)

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

                val staffArray = json.getJSONArray("data")
                val staffList = mutableListOf<StaffData>()
                for (i in 0 until staffArray.length()) {
                    val item = staffArray.getJSONObject(i)
                    val staff = StaffData(
                        id = item.getInt("id"),
                        email = item.getString("email"),
                        firstName = item.getString("first_name"),
                        lastName = item.getString("last_name"),
                        avatar = item.getString("avatar")
                    )
                    staffList.add(staff)
                }

                val respData = StaffListRespData(
                    page = json.getInt("page"),
                    perPage = json.getInt("per_page"),
                    total = json.getInt("total"),
                    totalPages = json.getInt("total_pages"),
                    data = staffList
                )

                completion(Result.success(respData))

            } catch (e: Exception) {
                completion(Result.failure(e))
            }
        }
    }
}
