package com.example.myapplication.view

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.core.utils.ValidationUtil
import com.example.myapplication.managers.LoginManager

@Composable
fun LoginPage(navController: NavController? = null) {
    var email by remember { mutableStateOf("eve.holt@reqres.in") }
    var password by remember { mutableStateOf("cityslicka") }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LOGIN",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Please enter email and password"
                    showError = true
                    return@Button
                }
                if (!ValidationUtil.isValidEmail(email)) {
                    errorMessage = "Invalid email format"
                    showError = true
                    return@Button
                }
                if (!ValidationUtil.isValidPassword(password)) {
                    errorMessage = "Password must be 6–10 characters, letters and numbers only"
                    showError = true
                    return@Button
                }

                isLoading = true

                LoginManager.login(email, password) { result ->
                    Handler(Looper.getMainLooper()).post {
                        isLoading = false
                        result.onSuccess { resp ->
                            val token = resp.token ?: ""
                            navController?.navigate("staffList/$token")
                        }.onFailure { error ->
                            errorMessage = error.message ?: "Login failed"
                            showError = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Logging in...", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                CircularProgressIndicator()
            }
        }

        if (showError) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFFFCCCC), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(errorMessage, color = Color.Red, modifier = Modifier.weight(1f))
                TextButton(onClick = { showError = false }) {
                    Text("OK")
                }
            }
        }
    }
}
