package com.example.algovizio.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.algovizio.algorithms.sorting.bubbleSort
import com.example.algovizio.algorithms.sorting.selectionSort
import com.example.algovizio.model.BarItemData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlgoViewModel : ViewModel() {
    // Counting Swaps
    var countSwap = mutableStateOf(0)
    // Numbers of Bars
    var noOfBars = mutableStateOf(10)

    // Pointers for comparison
    var iPointer = mutableStateOf(-1)
    var jPointer = mutableStateOf(-2)

    // is execution in process
    var isSorting = mutableStateOf(false)

    // Execution Speed
    var speed = mutableStateOf(500f)


    // Bar Colors
    val defaultColor = Color(0xFF6200EE) // Purple
    val activeColor = Color.Gray         // Gray (for swapping)
    val sortedColor = Color.Green       // Green (for finished)
    val currMinSelected = Color.Red     // Red (Current Min)

    // By Default List
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


    private var sortJob: Job? = null


    fun startBubbleSort(isFinished: () -> Unit) {
        sortJob?.cancel()
        sortJob = viewModelScope.launch {
            bubbleSort(
                sortingList,
                speed.value,
                defaultColor,
                activeColor,
                sortedColor,
                ::updateColor
            ){
                countSwap.value++
            }
        }
    }
    fun startSelectionSort(isFinished: () -> Unit) {
        sortJob?.cancel()
        sortJob = viewModelScope.launch {
            selectionSort(
                sortingList,
                speed.value,
                defaultColor,
                activeColor,
                sortedColor,
                currMinSelected,
                ::updateColor
            ){
                countSwap.value++
            }
        }
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