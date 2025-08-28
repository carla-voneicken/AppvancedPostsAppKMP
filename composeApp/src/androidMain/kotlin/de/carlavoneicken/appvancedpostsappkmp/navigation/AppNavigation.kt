package de.carlavoneicken.appvancedpostsappkmp.navigation

import androidx.compose.runtime.Composable
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
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "users"
    ) {
        // 1. Users List
        composable("users") {
            val viewModel: UsersViewModel = koinViewModel()
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
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val viewModel: PostsViewModel = koinViewModel { parametersOf(userId) }

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
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0

            // takeIf{!isNew} -> if it's a new post, pass null instead of 0
            val viewModel: PostDetailViewModel = koinViewModel { parametersOf(postId.takeIf { !isNew }, userId) }

            PostDetailScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}