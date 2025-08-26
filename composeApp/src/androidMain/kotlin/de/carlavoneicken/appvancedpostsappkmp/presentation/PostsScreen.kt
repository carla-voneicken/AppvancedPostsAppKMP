package de.carlavoneicken.appvancedpostsappkmp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.PostsViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    viewModel: PostsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var isShowingNewPost by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    if (isShowingNewPost) {
        LaunchedEffect(Unit) {
            navController.navigate("postDetail/new")
            isShowingNewPost = false
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Posts") },
                actions = {
                    TextButton(
                        onClick = {
                            isShowingNewPost = true
                        }
                    ) {
                        Text("Neuer Post")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // while the uiState is loading, display a circular progress indicator
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Black
                    )
                }
                // when the uiState is no longer loading, display the post
                else -> {
                    Column {
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(uiState.posts) { post ->
                                PostItem(post = post, navController = navController)
                            }
                        }

                        // if the errorMessage is not null or empty (aka there is an error), display it
                        if (!uiState.errorMessage.isNullOrEmpty()) {
                            Text(
                                text = uiState.errorMessage ?: "",
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Navigation Host
    NavHost(navController, startDestination = "posts") {
        composable("postDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            PostDetailScreen(postId = postId, userId = uiState.userId)
        }
        composable("postDetail/new") {
            PostDetailScreen(postId = null, userId = uiState.userId)
        }
    }
}
