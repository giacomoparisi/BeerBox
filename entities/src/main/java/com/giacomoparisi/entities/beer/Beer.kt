package com.giacomoparisi.entities.beer

data class Beer(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val imageUrl: String
)