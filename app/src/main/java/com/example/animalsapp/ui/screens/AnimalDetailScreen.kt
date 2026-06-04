package com.example.animalsapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
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
import com.example.animalsapp.ui.theme.*
import com.example.animalsapp.viewmodel.AnimalsViewModel
import com.example.animalsapp.viewmodel.UiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalDetailScreen(
    animalId: String,
    viewModel: AnimalsViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(animalId) {
        viewModel.loadAnimalDetail(animalId)
    }

    val state by viewModel.animalDetailState.collectAsState()

    when (val s = state) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(s.message) { viewModel.loadAnimalDetail(animalId) }
        is UiState.Success -> {
            val animal = s.data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    // Header image with back button overlay
                    Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                        AsyncImage(
                            model = animal.image,
                            contentDescription = animal.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, DarkBackground),
                                        startY = 150f
                                    )
                                )
                        )
                        // Back button
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(CircleShape)
                                .background(DarkCard.copy(alpha = 0.7f))
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = TextPrimary)
                        }
                        // Animal name
                        Text(
                            text = animal.name,
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
                    // Description
                    Text(
                        text = animal.description,
                        color = TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Facts section
                if (animal.facts.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(24.dp))
                        Text(
                            text = "Hechos Interesantes",
                            color = AccentYellow,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                    items(animal.facts) { fact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkCard)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = AccentYellow,
                                modifier = Modifier.size(18.dp).padding(top = 2.dp)
                            )
                            Text(text = fact, color = TextPrimary, fontSize = 13.sp, lineHeight = 20.sp)
                        }
                    }
                }

                // Gallery section
                if (animal.imageGallery.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(24.dp))
                        Text(
                            text = "Galería de Imágenes",
                            color = AccentYellow,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(Modifier.height(12.dp))

                        val pagerState = rememberPagerState(pageCount = { animal.imageGallery.size })
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth().height(220.dp),
                            contentPadding = PaddingValues(horizontal = 32.dp),
                            pageSpacing = 12.dp
                        ) { page ->
                            AsyncImage(
                                model = animal.imageGallery[page],
                                contentDescription = "Galería ${page + 1}",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Pager dots indicator
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(animal.imageGallery.size) { index ->
                                val isSelected = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 3.dp)
                                        .size(if (isSelected) 10.dp else 7.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) AccentYellow else TextSecondary)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
