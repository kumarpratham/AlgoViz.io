package com.example.algovizio.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.algovizio.model.BarItemData
import com.example.algovizio.model.SortAlgorithm
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlgoViewModel : ViewModel() {

    var countSwap = mutableStateOf(0)

    var iPointer = mutableStateOf(-1)
    var jPointer = mutableStateOf(-2)

    var noOfBars = mutableStateOf(10)

    var isSorting = mutableStateOf(false)

    var speed = mutableStateOf(500f)

    val defaultColor = Color(0xFF6200EE) // Purple
    val activeColor = Color.Gray         // Gray (for swapping)
    val sortedColor = Color.Green       // Green (for finished)
    val currMinSelected = Color.Red     // Red (Current Min)


    val sortingList = mutableStateListOf(
        BarItemData(0, 40, defaultColor),
        BarItemData(1, 90, defaultColor),
        BarItemData(2, 30, defaultColor),
        BarItemData(3, 10, defaultColor),
        BarItemData(4, 70, defaultColor),
        BarItemData(5, 20, defaultColor),
        BarItemData(6, 80, defaultColor),
        BarItemData(7, 60, defaultColor),
        BarItemData(8, 50, defaultColor)
    )
    val selectedAlgorithm = mutableStateOf(SortAlgorithm.BUBBLE)


    private var sortJob: Job? = null


    fun startBubbleSort(isFinished: () -> Unit) {
        sortJob?.cancel()
        sortJob = viewModelScope.launch {
            bubbleSort()
        }
    }
    fun startSelectionSort(isFinished: () -> Unit) {
        sortJob?.cancel()
        sortJob = viewModelScope.launch {
            selectionSort()
        }
    }

    private suspend fun bubbleSort() {
        val n = sortingList.size
        // Outer Loop
        for (i in 0 until n - 1) {

            // Inner Loop
            for (j in 0 until n - i - 1) {

                // 1. HIGHLIGHT: Turn the two bars being compared GRAY
                updateColor(j, activeColor)
                updateColor(j + 1, activeColor)

                delay(speed.value.toLong()) // Wait so user sees the comparison

                // 2. COMPARE & SWAP
                if (sortingList[j].value > sortingList[j + 1].value) {
                    val temp = sortingList[j]
                    sortingList[j] = sortingList[j + 1]
                    sortingList[j + 1] = temp
                    countSwap.value++
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

    private suspend fun selectionSort() {
        val n = sortingList.size

        for (i in 0 until n - 1) {

            // 1 Mark current index i
            var currMin = i
            updateColor(currMin, currMinSelected)

            // 2️ Scan unsorted part
            for (j in i + 1 until n) {

                // Highlight element being compared
                updateColor(j, activeColor)
                delay(speed.value.toLong())

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
                countSwap.value++
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



    // Add and Delete BARS
    fun addBar() {
        noOfBars.value++
        resetList()
    }

    fun deleteBar() {
        if (noOfBars.value <= 2) return
        noOfBars.value--
        resetList()
    }

    // Helper to reset the list so we can run it again
    fun resetList() {
        sortJob?.cancel()
        sortingList.clear()
        isSorting.value = false
        countSwap.value = 0
        iPointer.value = -1
        jPointer.value = -2

        val randomBars = List(noOfBars.value) { index ->
            BarItemData(
                index,
                (1..100).random(),
                defaultColor
            )
        }
        sortingList.addAll(randomBars)
    }

    // Helper to change color of a single bar
    fun updateColor(index: Int, newColor: Color) {
        val item = sortingList[index]
        sortingList[index] = item.copy(color = newColor)
    }
}