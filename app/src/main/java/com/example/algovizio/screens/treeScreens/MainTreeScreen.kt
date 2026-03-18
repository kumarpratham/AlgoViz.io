package com.example.algovizio.screens.treeScreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.algovizio.components.ListComponent
import com.example.algovizio.model.Algorithms

@Composable
fun MainTreeScreen(navController: NavController){
    val items = Algorithms.treeAlgorithms

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        itemsIndexed(items) {index, item ->

            ListComponent(item.first,index = index, onClick = {
                navController.navigate("TREE_VISUALIZER/${ item.second}")
            })
        }
    }
}