package com.example.animalsapp.data.network

import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.data.model.Environment
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimalsApiService {

    @GET("animals")
    suspend fun getAnimals(): List<Animal>

    @GET("animals/{id}")
    suspend fun getAnimalById(@Path("id") id: String): Animal

    @GET("animals")
    suspend fun getAnimalsByEnvironment(@Query("enviromentId") environmentId: String): List<Animal>

    @GET("enviroment")
    suspend fun getEnvironments(): List<Environment>

    @GET("enviroment/{id}")
    suspend fun getEnvironmentById(@Path("id") id: String): Environment
}
