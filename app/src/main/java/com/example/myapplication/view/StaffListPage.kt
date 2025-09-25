package com.example.myapplication.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.managers.StaffManager
import com.example.myapplication.models.data.StaffData
import com.example.myapplication.models.network.staff.StaffListRespData
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.components.StaffRowView

@Composable
fun StaffListPage(
    token: String,
    navController: NavController? = null
) {
    var staffData by remember { mutableStateOf<List<StaffData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(1) }
    var totalPages by remember { mutableStateOf(1) }
    val perPage = 10
    var isLoadingMore by remember { mutableStateOf(false) }

    // Load first page
    LaunchedEffect(Unit) {
        fetchStaffList(
            page = 1,
            perPage = perPage,
            onSuccess = { resp ->
                staffData = resp.data
                currentPage = resp.page
                totalPages = resp.totalPages
                isLoading = false
                hasError = false
            },
            onError = {
                isLoading = false
                hasError = true
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Token header
        Text(
            text = "Token: $token",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            hasError || staffData.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No staff found or error occurred")
                }
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(staffData) { staff ->
                        StaffRowView(staff = staff)
                    }
                    // Load more section
                    if (currentPage < totalPages) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isLoadingMore) {
                                    CircularProgressIndicator()
                                } else {
                                    Button(onClick = {
                                        isLoadingMore = true
                                        fetchStaffList(
                                            page = currentPage + 1,
                                            perPage = perPage,
                                            onSuccess = { resp ->
                                                staffData = staffData + resp.data
                                                currentPage = resp.page
                                                totalPages = resp.totalPages
                                                isLoadingMore = false
                                                hasError = false
                                            },
                                            onError = {
                                                isLoadingMore = false
                                                hasError = true
                                            }
                                        )
                                    }) {
                                        Text("Load More")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Helper to wrap StaffManager in a clean way
 */
private fun fetchStaffList(
    page: Int,
    perPage: Int,
    onSuccess: (StaffListRespData) -> Unit,
    onError: (Throwable) -> Unit
) {
    StaffManager.getStaffList(page, perPage) { result ->
        result.onSuccess { resp ->
            onSuccess(resp)
        }.onFailure { error ->
            onError(error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    MyApplicationTheme {
        StaffListPage(token = "")
    }
}
