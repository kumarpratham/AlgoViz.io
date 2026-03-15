package com.example.algovizio.screens.sortingScreens

// If your Compose version is older, you might need this for animation:
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.algovizio.components.AdjustmentButtons
import com.example.algovizio.components.Bars
import com.example.algovizio.model.Algorithms
import com.example.algovizio.viewmodels.AlgoViewModel

@Composable
fun SortingScreen(navController: NavController,viewModel: AlgoViewModel = viewModel(),algorithm : String) {


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
//            val algo = when(algorithm){
//                items[0].second -> items[0].first
//                items[1].second -> items[1].first
//                else -> ""
//            }
            val items = Algorithms.sortingAlgorithms
            val algo = items.find { it.second == algorithm }?.first ?: ""
            Text(
                text = "Sorting Algorithm: $algo",
                fontSize = 20.sp,
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
        Text(
            "Bars Count: ${viewModel.sortingList.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)

        )
        Spacer(modifier = Modifier.height(32.dp))

        // 1. THE VISUALIZER (The Bars)
        // We use a Row to place bars side-by-side
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp), // Fixed height for the container
//            horizontalArrangement = Arrangement.SpaceEvenly, // Space them out nicely
//            verticalAlignment = Alignment.Bottom // Grow bars from the bottom up
//        ) {
//            viewModel.sortingList.forEachIndexed { index, value ->
//                // --- COLOR LOGIC ---
//                val isBeingCompared = (index == viewModel.jPointer.value || index == viewModel.jPointer.value + 1)
//                val isAlreadySorted = (index >= viewModel.sortingList.size - viewModel.iPointer.value)
//
//                val barColor = when {
//                    isBeingCompared -> if(!viewModel.ifSwaping.value) Color.Red else Color.DarkGray   // Active "Scanner" pair
//                    isAlreadySorted -> Color.Green  // Locked in place (Sorted)
//                    else -> MaterialTheme.colorScheme.primary // Default (Unsorted)
//                }
//                Bars(value, barColor)
//            }
//        }
//        var arrange = Arrangement.SpaceEvenly
//        if(viewModel.sortingList.size > 15){
//            arrange = Arrangement.spacedBy(12.dp)
//        }
        val dynamicSpace = when {
            viewModel.sortingList.size > 40 -> 2.dp
            viewModel.sortingList.size > 20 -> 8.dp
            else -> 16.dp
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dynamicSpace),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Set a fixed height for the sorting area
        ) {

            // NOTICE: We use 'items' with a 'key'. This is the secret sauce!
            items(
                items = viewModel.sortingList,
                key = { it.id }
            ) { barData ->
                // This 'Box' wraps your Bar and Text
                Box(
                    modifier = Modifier
                        .animateItem() // <--- THIS MAKES IT SLIDE!
                    // If '.animateItem()' is red, try '.animateItemPlacement()'
                ) {
                    // Your existing BarItem Composable goes here
                    // You will need to update BarItem to accept 'barData' instead of just Ints
                    Bars(
                        barData = barData,
                        barColor = barData.color // Pass your new gray/red/green color here
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))
        // 2. THE CONTROLS
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            Button(
                onClick = {
                    viewModel.isSorting.value = true
//                    viewModel.startBubbleSort { viewModel.isSorting.value = false }
                    when (algorithm) {
                        "bubble" ->
                            viewModel.startBubbleSort {
                                viewModel.isSorting.value = false
                            }

                        "selection" ->
                            viewModel.startSelectionSort {
                                viewModel.isSorting.value = false
                            }
                    }
                },
                enabled = !viewModel.isSorting.value
            ) {
                Text("Start Sort")
            }

            Button(onClick = { viewModel.resetList() }) {
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

