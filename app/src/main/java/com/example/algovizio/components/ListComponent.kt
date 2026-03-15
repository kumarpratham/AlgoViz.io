package com.example.algovizio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algovizio.ui.theme.RandomColors

@Composable
fun ListComponent(
    title: String,
    index: Int,
    onClick: () -> Unit
) {

    val color = RandomColors[index % RandomColors.size]

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {

        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp),
            color = color
        )

    }
}