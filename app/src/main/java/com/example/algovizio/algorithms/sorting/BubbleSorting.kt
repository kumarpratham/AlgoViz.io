package com.example.algovizio.algorithms.sorting

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.algovizio.model.BarItemData
import kotlinx.coroutines.delay

suspend fun bubbleSort(
    sortingList: SnapshotStateList<BarItemData>,
    speed: Float,
    defaultColor: Color,
    activeColor: Color,
    sortedColor: Color,
    updateColor: (Int, Color) -> Unit,
    onSwap: () -> Unit
) {
    val n = sortingList.size
    // Outer Loop
    for (i in 0 until n - 1) {

        // Inner Loop
        for (j in 0 until n - i - 1) {

            // 1. HIGHLIGHT: Turn the two bars being compared GRAY
            updateColor(j, activeColor)
            updateColor(j + 1, activeColor)

            delay(speed.toLong()) // Wait so user sees the comparison

            // 2. COMPARE & SWAP
            if (sortingList[j].value > sortingList[j + 1].value) {
                val temp = sortingList[j]
                sortingList[j] = sortingList[j + 1]
                sortingList[j + 1] = temp
                onSwap()
            }

            // 3. RESET: Turn them back to PURPLE before moving to next pair
            // (Only reset if we are not at the very end of the loop)
            updateColor(j, defaultColor)
            updateColor(j + 1, defaultColor)
        }

        // 4. SORTED: The bar at the end (n - i - 1) is now guaranteed correct.
        // Turn it GREEN and leave it Green!
        updateColor(n - i - 1, sortedColor)
    }

    // 5. FINAL TOUCH: The very first bar is technically sorted now too
    updateColor(0, sortedColor)

}