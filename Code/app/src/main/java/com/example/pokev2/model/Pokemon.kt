package com.example.pokev2.model

data class Pokemon(
    val game_index: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val height: String,
    val weight: String,
    val gender: String = "Unknown", // Default value
    val base_experience: Int = 0,          // Default value
    val category: String = "Unknown",
    val cycles: String = "Unknown",
    val defense: Int = 0,
    val eggGroups: String = "Unknown",
    val evolutions: List<String> = emptyList(),
    val evolvedFrom: String = "None",
    val hp: Int = 0,
    val reason: String = "N/A",
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0,
    val total: Int = 0,
    val weaknesses: List<String> = emptyList(),
    val xDescription: String = "N/A",
    val yDescription: String = "N/A"
)
