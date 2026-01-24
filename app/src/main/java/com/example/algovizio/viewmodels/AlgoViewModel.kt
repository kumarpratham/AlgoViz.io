package com.example.algovizio.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlgoViewModel : ViewModel(){
    val sortingList = mutableStateListOf(50,20,80,10,60,30,40,70,90)
    var countSwap = mutableStateOf(0)

    var iPointer = mutableStateOf(-1)
    var jPointer = mutableStateOf(-2)

    var noOfBars = mutableStateOf(10)

    var isSorting = mutableStateOf(false)

    var speed = mutableStateOf(500f)
    var ifSwaping = mutableStateOf(false)


    private var sortJob : Job? = null





    fun startBubbleSort(onFinish: () -> Unit){

        sortJob?.cancel()
        sortJob = viewModelScope.launch {
            bubbleSort()
        }
    }

    private suspend fun bubbleSort(){
        val n = sortingList.size
        for(i in 0 until n-1){

            iPointer.value = i
            for(j in 0 until n-i-1){
                jPointer.value = j
                if(sortingList[j] > sortingList[j+1]){
                    // Color logic to highlight the swap and Sorted Array
                    ifSwaping.value = true
                    delay(400)
                    // Swap
                    val temp = sortingList[j]
                    sortingList[j] = sortingList[j+1]
                    sortingList[j+1] = temp
                    countSwap.value++
                    delay(400)
                    ifSwaping.value = false
                }

                delay(speed.value.toLong())

            }
        }
        // Color logic to highlight the Sorted Array
        iPointer.value = n-1
        jPointer.value = -1

    }
    // Add and Delete BARS
    fun addBar(){
        noOfBars.value++
        resetList()
    }
    fun  deleteBar(){
        if(noOfBars.value <= 2) return
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

        val randomValues = List(noOfBars.value) { (10..100).random() }
        sortingList.addAll(randomValues)
    }
}