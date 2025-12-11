package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<Genre>,
)

@Serializable
data class Genre(
    val id: Long,
    val name: String,
)