package com.example.animalsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.data.model.Environment
import com.example.animalsapp.data.repository.AnimalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class AnimalsViewModel : ViewModel() {

    private val repository = AnimalsRepository()

    private val _animalsState = MutableStateFlow<UiState<List<Animal>>>(UiState.Loading)
    val animalsState: StateFlow<UiState<List<Animal>>> = _animalsState

    private val _environmentsState = MutableStateFlow<UiState<List<Environment>>>(UiState.Loading)
    val environmentsState: StateFlow<UiState<List<Environment>>> = _environmentsState

    private val _animalDetailState = MutableStateFlow<UiState<Animal>>(UiState.Loading)
    val animalDetailState: StateFlow<UiState<Animal>> = _animalDetailState

    private val _environmentDetailState = MutableStateFlow<UiState<Environment>>(UiState.Loading)
    val environmentDetailState: StateFlow<UiState<Environment>> = _environmentDetailState

    private val _environmentAnimalsState = MutableStateFlow<UiState<List<Animal>>>(UiState.Loading)
    val environmentAnimalsState: StateFlow<UiState<List<Animal>>> = _environmentAnimalsState

    init {
        loadAnimals()
        loadEnvironments()
    }

    fun loadAnimals() {
        viewModelScope.launch {
            _animalsState.value = UiState.Loading
            repository.getAnimals().fold(
                onSuccess = { _animalsState.value = UiState.Success(it) },
                onFailure = { _animalsState.value = UiState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun loadEnvironments() {
        viewModelScope.launch {
            _environmentsState.value = UiState.Loading
            repository.getEnvironments().fold(
                onSuccess = { _environmentsState.value = UiState.Success(it) },
                onFailure = { _environmentsState.value = UiState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun loadAnimalDetail(id: String) {
        viewModelScope.launch {
            _animalDetailState.value = UiState.Loading
            repository.getAnimalById(id).fold(
                onSuccess = { _animalDetailState.value = UiState.Success(it) },
                onFailure = { _animalDetailState.value = UiState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun loadEnvironmentDetail(id: String) {
        viewModelScope.launch {
            _environmentDetailState.value = UiState.Loading
            repository.getEnvironmentById(id).fold(
                onSuccess = {
                    _environmentDetailState.value = UiState.Success(it)
                    loadAnimalsByEnvironment(id)
                },
                onFailure = { _environmentDetailState.value = UiState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    private fun loadAnimalsByEnvironment(environmentId: String) {
        viewModelScope.launch {
            _environmentAnimalsState.value = UiState.Loading
            repository.getAnimalsByEnvironment(environmentId).fold(
                onSuccess = { _environmentAnimalsState.value = UiState.Success(it) },
                onFailure = { _environmentAnimalsState.value = UiState.Error(it.message ?: "Error desconocido") }
            )
        }
    }
}
