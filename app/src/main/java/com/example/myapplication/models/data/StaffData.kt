package com.example.myapplication.models.data

data class StaffData(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
) {
    companion object {
        fun fromJson(json: org.json.JSONObject): StaffData {
            return StaffData(
                id = json.getInt("id"),
                email = json.getString("email"),
                firstName = json.getString("first_name"),
                lastName = json.getString("last_name"),
                avatar = json.getString("avatar")
            )
        }
    }
}
