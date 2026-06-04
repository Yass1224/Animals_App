package com.example.animalsapp.data.repository

import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.data.model.Environment
import com.example.animalsapp.data.network.RetrofitClient

class AnimalsRepository {

    private val api = RetrofitClient.apiService

    suspend fun getAnimals(): Result<List<Animal>> = runCatching { api.getAnimals() }

    suspend fun getAnimalById(id: String): Result<Animal> = runCatching { api.getAnimalById(id) }

    suspend fun getAnimalsByEnvironment(environmentId: String): Result<List<Animal>> =
        runCatching { api.getAnimalsByEnvironment(environmentId) }

    suspend fun getEnvironments(): Result<List<Environment>> = runCatching { api.getEnvironments() }

    suspend fun getEnvironmentById(id: String): Result<Environment> =
        runCatching { api.getEnvironmentById(id) }
}
