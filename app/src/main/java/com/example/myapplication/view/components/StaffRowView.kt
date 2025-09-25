package com.example.myapplication.view.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
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
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }

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
        Box(
            modifier = Modifier
                .padding(8.dp) // 👈 outer padding for the whole avatar
                .size(60.dp)   // total size including padding
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "\uD83D\uDC64", // 👤 placeholder
                    color = Color.DarkGray,
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

@Preview(showBackground = true)
@Composable
fun StaffRowViewPreview() {
    StaffRowView(
        staff = StaffData(id = 1, email = "test", firstName = "firstName",
            lastName = "lastName", avatar = "")
    )
}
