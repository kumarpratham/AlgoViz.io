package com.example.algovizio.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.algovizio.screens.MainPageScreen
import com.example.algovizio.screens.sortingScreens.MainSortingScreen
import com.example.algovizio.screens.sortingScreens.SortingScreen
import com.example.algovizio.screens.treeScreens.MainTreeScreen
import com.example.algovizio.screens.treeScreens.TreeScreen


@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        // Main Page
        composable(Routes.MAIN) {
            MainPageScreen(navController)
        }

        // Sorting main screen
        composable(Routes.SORTING_MAIN) {
            MainSortingScreen(navController)
        }

        // Sorting visualizer
        composable(
            route = "${Routes.SORTING_VISUALIZER}/{algorithm}",
            arguments = listOf(
                navArgument("algorithm") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val algorithm = backStackEntry.arguments?.getString("algorithm") ?: ""

            SortingScreen(
                navController = navController,
                algorithm = algorithm
            )
        }

        // Tree main screen
        composable(Routes.TREE_MAIN) {
            MainTreeScreen(navController)
        }
        // Tree visualizer
        composable(
//            TreeScreen(navController)

            route = "${Routes.TREE_VISUALIZER}/{algorithm}",
            arguments = listOf(
                navArgument("algorithm") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val algorithm = backStackEntry.arguments?.getString("algorithm") ?: ""

            TreeScreen(
                navController = navController,
                algorithm = algorithm
            )
        }
    }
}

/*
NavHost(
modifier = modifier,
navController = navController,
startDestination = Profile
) {
    composable<Profile> {
        ProfileScreen(
            onNavigateToFriends = { navController.navigate(route = FriendsList) },
            */
/*...*//*
        )
    }
    composable<FriendsList> { FriendsListScreen(*/
/*...*//*
    ) }
}*/
