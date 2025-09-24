package com.example.myapplication.models.network.staff

import com.example.myapplication.models.data.StaffData

import org.json.JSONObject

data class StaffListRespData(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<StaffData>,
    val error: String? = null
) {
    companion object {
        fun fromJson(json: JSONObject): StaffListRespData {
            val staffArray = json.optJSONArray("data")
            val staffList = mutableListOf<StaffData>()
            if (staffArray != null) {
                for (i in 0 until staffArray.length()) {
                    val item = staffArray.getJSONObject(i)
                    staffList.add(StaffData.fromJson(item))
                }
            }

            return StaffListRespData(
                page = json.optInt("page", 0),
                perPage = json.optInt("per_page", 0),
                total = json.optInt("total", 0),
                totalPages = json.optInt("total_pages", 0),
                data = staffList,
                error = json.optString("error", null)
            )
        }
    }
}