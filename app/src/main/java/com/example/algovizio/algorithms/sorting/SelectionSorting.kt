package com.example.algovizio.algorithms.sorting

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.algovizio.model.BarItemData
import kotlinx.coroutines.delay

suspend fun selectionSort(
    sortingList: SnapshotStateList<BarItemData>,
    speed: Float,
    defaultColor: Color,
    activeColor: Color,
    sortedColor: Color,
    currMinSelected: Color,
    updateColor: (Int, Color) -> Unit,
    onSwap: () -> Unit
) {
    val n = sortingList.size

    for (i in 0 until n - 1) {

        // 1 Mark current index i
        var currMin = i
        updateColor(currMin, currMinSelected)

        // 2️ Scan unsorted part
        for (j in i + 1 until n) {

            // Highlight element being compared
            updateColor(j, activeColor)
            delay(speed.toLong())

            if (sortingList[j].value < sortingList[currMin].value) {
                // Remove old min highlight (unless it's i)
                if (currMin != i) {
                    updateColor(currMin, defaultColor)
                }
                currMin = j
                updateColor(currMin, currMinSelected)
            } else {
                updateColor(j, defaultColor)
            }
        }

        // 3 Swap min with i
        if (currMin != i) {
            val temp = sortingList[currMin]
            sortingList[currMin] = sortingList[i]
            sortingList[i] = temp
            onSwap()
        }

        // 4 Lock sorted position
        updateColor(i, sortedColor)

        // Reset color of swapped element if needed
        if (currMin != i) {
            updateColor(currMin, defaultColor)
        }
    }

    // 5 Last element is automatically sorted
    updateColor(n - 1, sortedColor)
}