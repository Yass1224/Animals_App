package com.example.animalsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalsapp.ui.components.EnvironmentCard
import com.example.animalsapp.ui.components.ErrorScreen
import com.example.animalsapp.ui.components.LoadingScreen
import com.example.animalsapp.ui.theme.TextSecondary
import com.example.animalsapp.viewmodel.AnimalsViewModel
import com.example.animalsapp.viewmodel.UiState

@Composable
fun EnvironmentListScreen(
    viewModel: AnimalsViewModel,
    onEnvironmentClick: (String) -> Unit
) {
    val state by viewModel.environmentsState.collectAsState()

    when (val s = state) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(s.message) { viewModel.loadEnvironments() }
        is UiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    Text(
                        text = "Ambientes",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Explora los hábitats naturales del mundo",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(8.dp))
                }
                items(s.data) { environment ->
                    EnvironmentCard(
                        environment = environment,
                        onClick = { onEnvironmentClick(environment.id) }
                    )
                }
            }
        }
    }
}
