package de.carlavoneicken.appvancedpostsappkmp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.PostDetailViewModel
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.PostsViewModel
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.UsersViewModel
import de.carlavoneicken.appvancedpostsappkmp.presentation.PostDetailScreen
import de.carlavoneicken.appvancedpostsappkmp.presentation.PostsScreen
import de.carlavoneicken.appvancedpostsappkmp.presentation.UsersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "users"
    ) {
        // 1. Users List
        composable("users") {
            val viewModel: UsersViewModel = viewModel()
            UsersScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // 2. Posts List
        composable(
            route = "posts/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0
            val viewModel: PostsViewModel = viewModel { PostsViewModel(userId) }

            PostsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        // 3. Post Detail View
        composable(
            route = "post_detail/{isNew}/{userId}/{postId}",
            arguments = listOf(
                navArgument("isNew") { type = NavType.BoolType },
                navArgument("userId") { type = NavType.IntType },
                navArgument("postId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val isNew = backStackEntry.arguments?.getBoolean("isNew") ?: false
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0
            val postId = backStackEntry.arguments?.getLong("postId") ?: 0

            // takeIf{!isNew} -> if it's a new post, pass null instead of 0
            val viewModel: PostDetailViewModel = viewModel { PostDetailViewModel(postId.takeIf { !isNew }, userId) }

            PostDetailScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}