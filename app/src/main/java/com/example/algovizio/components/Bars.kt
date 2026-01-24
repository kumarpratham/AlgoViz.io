package com.example.algovizio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Bars(value : Int, barColor : androidx.compose.ui.graphics.Color){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
//                .height((value * 3).dp) // Multiply by 3 to make them taller!
                .fillMaxHeight((value/100f) *.85f)
                .background(
                    color = barColor,
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text("$value",
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}