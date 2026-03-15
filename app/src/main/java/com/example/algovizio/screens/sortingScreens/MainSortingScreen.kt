package com.example.algovizio.screens.sortingScreens


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.algovizio.components.LazyList
import com.example.algovizio.components.ListComponent
import com.example.algovizio.model.Algorithms

@Composable


fun MainSortingScreen(navController: NavController) {

    val items = Algorithms.sortingAlgorithms

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        itemsIndexed(items) {index, item ->

            ListComponent(item.first,index = index, onClick = {
                navController.navigate("SORTING_VISUALIZER/${ item.second}")
            })
        }
    }
}