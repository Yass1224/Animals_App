package com.example.animalsapp.data.model

import com.google.gson.annotations.SerializedName

data class Animal(
    @SerializedName("_id") val id: String,
    val name: String,
    val image: String,
    val description: String,
    val imageGallery: List<String> = emptyList(),
    val environmentId: String = "",
    val facts: List<String> = emptyList()
)

data class Environment(
    @SerializedName("_id") val id: String,
    val name: String,
    val image: String,
    val description: String
)
