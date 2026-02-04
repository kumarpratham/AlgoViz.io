package com.example.algovizio.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.algovizio.model.SortAlgorithm
import com.example.algovizio.viewmodels.AlgoViewModel

@Composable
fun AlgorithmSelector(viewModel: AlgoViewModel) {
    val expanded = remember { mutableStateOf(false) }

    Surface(
        color = Color(0xFF6200EE).copy(alpha = 0.1f),
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(1.dp, Color(0xFF6200EE).copy(alpha = 0.5f))
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

            Text(
                text = viewModel.selectedAlgorithm.value.displayName,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .align(Alignment.Center)
                    .clickable { expanded.value = true },
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                SortAlgorithm.entries.forEach { algo ->
                    DropdownMenuItem(
                        text = { Text(algo.displayName) },
                        onClick = {
                            viewModel.selectedAlgorithm.value = algo
                            viewModel.resetList()
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}
