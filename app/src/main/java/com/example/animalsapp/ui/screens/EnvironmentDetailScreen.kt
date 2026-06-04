package com.example.animalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.animalsapp.ui.components.ErrorScreen
import com.example.animalsapp.ui.components.LoadingScreen
import com.example.animalsapp.ui.components.SmallAnimalCard
import com.example.animalsapp.ui.theme.*
import com.example.animalsapp.viewmodel.AnimalsViewModel
import com.example.animalsapp.viewmodel.UiState

@Composable
fun EnvironmentDetailScreen(
    environmentId: String,
    viewModel: AnimalsViewModel,
    onBack: () -> Unit,
    onAnimalClick: (String) -> Unit
) {
    LaunchedEffect(environmentId) {
        viewModel.loadEnvironmentDetail(environmentId)
    }

    val state by viewModel.environmentDetailState.collectAsState()
    val animalsState by viewModel.environmentAnimalsState.collectAsState()

    when (val s = state) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(s.message) { viewModel.loadEnvironmentDetail(environmentId) }
        is UiState.Success -> {
            val environment = s.data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                        AsyncImage(
                            model = environment.image,
                            contentDescription = environment.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, DarkBackground),
                                        startY = 160f
                                    )
                                )
                        )
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(CircleShape)
                                .background(DarkCard.copy(alpha = 0.7f))
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = TextPrimary)
                        }
                        Text(
                            text = environment.name,
                            color = TextPrimary,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = environment.description,
                        color = TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "Animales de este ambiente",
                        color = AccentYellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(12.dp))

                    when (val aState = animalsState) {
                        is UiState.Loading -> {
                            Box(Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = AccentYellow, modifier = Modifier.size(32.dp))
                            }
                        }
                        is UiState.Error -> {
                            Text(
                                text = "No se pudieron cargar los animales",
                                color = TextSecondary,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        is UiState.Success -> {
                            if (aState.data.isEmpty()) {
                                Text(
                                    text = "No hay animales registrados en este ambiente",
                                    color = TextSecondary,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            } else {
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(aState.data) { animal ->
                                        SmallAnimalCard(
                                            animal = animal,
                                            onClick = { onAnimalClick(animal.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
