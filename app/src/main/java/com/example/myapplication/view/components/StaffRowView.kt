package com.example.myapplication.view.components

import androidx.compose.ui.draw.clip
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.models.data.StaffData
import java.net.URL
import kotlin.concurrent.thread

@Composable
fun StaffRowView(staff: StaffData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Load image from URL (native way)
        var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

        LaunchedEffect(staff.avatar) {
            thread {
                try {
                    val url = URL(staff.avatar)
                    val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    bitmap = bmp
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        if (bitmap != null) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
                    .clip(CircleShape)
            )
        } else {
            // Placeholder
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
                    .clip(CircleShape)
            ) {
                Text(
                    text = "\uD83D\uDC64", // simple placeholder emoji
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(
                    text = staff.firstName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = staff.lastName,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = staff.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
