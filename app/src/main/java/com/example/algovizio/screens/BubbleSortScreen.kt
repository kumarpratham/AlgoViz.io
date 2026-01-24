package com.example.algovizio.screens


import android.R.attr.enabled
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.algovizio.components.AdjustmentButtons
import com.example.algovizio.components.Bars
import com.example.algovizio.viewmodels.AlgoViewModel

@Composable
fun BubbleSortScreen(viewModel: AlgoViewModel = viewModel()) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            // 1. Light purple background (take your main color and add .copy(alpha = 0.1f))
            color = Color(0xFF6200EE).copy(alpha = 0.1f),

            // 2. Fully rounded corners
            shape = RoundedCornerShape(percent = 50),

            // 3. A subtle border for definition
            border = BorderStroke(1.dp, Color(0xFF6200EE).copy(alpha = 0.5f)),

            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp) // Spacing from top
        ) {
            Text(
                text = "Bubble Sort Visualization",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp), // Inner padding
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE), // Dark purple text
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Swaps: ${viewModel.countSwap.value}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)

        )
        Spacer(modifier = Modifier.height(32.dp))

        // 1. THE VISUALIZER (The Bars)
        // We use a Row to place bars side-by-side
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // Fixed height for the container
            horizontalArrangement = Arrangement.SpaceEvenly, // Space them out nicely
            verticalAlignment = Alignment.Bottom // Grow bars from the bottom up
        ) {
            viewModel.sortingList.forEachIndexed { index, value ->
                // Each number is a Box with a specific height
                // 1. Determine the Color
//                val barColor = when {
//                    // Are we looking at this pair right now? -> RED
//                    index == viewModel.jPointer.value || index == viewModel.jPointer.value + 1 -> Color.Red
//
//                    // Is this part already sorted? (Bubble sort locks the end first) -> GREEN
//                    index >= viewModel.sortingList.size - viewModel.iPointer.value -> Color.Green
//
//                    // Default -> YOUR PURPLE
//                    else -> MaterialTheme.colorScheme.primary
//                }
                // --- COLOR LOGIC ---
                val isBeingCompared = (index == viewModel.jPointer.value || index == viewModel.jPointer.value + 1)
                val isAlreadySorted = (index >= viewModel.sortingList.size - viewModel.iPointer.value)

                val barColor = when {
                    isBeingCompared -> if(!viewModel.ifSwaping.value) Color.Red else Color.DarkGray   // Active "Scanner" pair
                    isAlreadySorted -> Color.Green  // Locked in place (Sorted)
                    else -> MaterialTheme.colorScheme.primary // Default (Unsorted)
                }
                Bars(value, barColor)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
         // 2. THE CONTROLS
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            Button(onClick = {
                viewModel.isSorting.value = true
                viewModel.startBubbleSort {viewModel.isSorting.value = false}},
                enabled = !viewModel.isSorting.value
            ) {
                Text("Start Sort")
            }

            Button(onClick = { viewModel.resetList()}) {
                Text("Reset / Shuffle")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        // Add and Delete Bars
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AdjustmentButtons(Icons.Filled.Add, onClick = { viewModel.addBar() })
            AdjustmentButtons(Icons.Filled.Remove, onClick = { viewModel.deleteBar() })
        }
        Spacer(modifier = Modifier.height(32.dp))
        Slider(
            value = viewModel.speed.value,
            onValueChange = { viewModel.speed.value = it },
            valueRange = 100f..2000f // 100ms (fast) to 2000ms (slow)
        )


    }
}

