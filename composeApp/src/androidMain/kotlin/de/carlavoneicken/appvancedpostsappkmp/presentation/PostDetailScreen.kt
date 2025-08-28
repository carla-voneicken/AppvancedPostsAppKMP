package de.carlavoneicken.appvancedpostsappkmp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.carlavoneicken.appvancedpostsappkmp.business.viewmodels.PostDetailViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = koinViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    // Show success messsage when post is saved, then navigate back to the previous screen
    var showSuccessText by remember { mutableStateOf(false) }
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            showSuccessText = true
            delay(1500)
            navController.popBackStack()
        }
    }

    // Show error alert dialog
    if (uiState.showError) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissError() },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissError() }) {
                    Text("OK")
                }
            },
            title = { Text("Fehler") },
            text = { Text(uiState.errorMessage ?: "Ein unbekannter Fehler ist aufgetreten.") }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription =  "Back")
                    }
                },
                actions = {
                    Button(onClick = { viewModel.savePost() }) {
                        Text("Speichern")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Titel", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text("Inhalt", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = uiState.body,
                onValueChange = { viewModel.updateBody(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10,
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.weight(1f))
            if (showSuccessText) {
                Text(
                    "🥳 Post gespeichert 🥳",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Green,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
























