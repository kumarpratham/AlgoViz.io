package com.example.algovizio.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.algovizio.components.LazyList
import com.example.algovizio.model.Algorithms

@Composable
fun MainPageScreen(navController: NavController) {

    val items = Algorithms.mainList
    LazyList(items, navController)
}