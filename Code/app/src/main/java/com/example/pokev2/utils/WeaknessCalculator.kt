package com.example.pokev2.utils

object WeaknessCalculator {
    private val typeWeaknessMap = mapOf(
        "fire" to listOf("water", "rock", "ground"),
        "water" to listOf("electric", "grass"),
        "grass" to listOf("fire", "bug", "ice", "poison"),
        "electric" to listOf("ground"),
        "normal" to listOf("fighting"),
        "poison" to listOf("psychic", "ground"),
        "psychic" to listOf("bug", "ghost", "dark"),
        "ghost" to listOf("ghost", "dark"),
        "fighting" to listOf("psychic", "flying", "fairy"),
        "fairy" to listOf("poison", "steel"),
        "ground" to listOf("water", "grass", "ice"),
        "rock" to listOf("water", "grass", "fighting", "ground", "steel"),
        "bug" to listOf("fire", "flying", "rock"),
        "dark" to listOf("fighting", "bug", "fairy")
    )

    fun calculateWeaknesses(types: List<String>): List<String> {
        return types.flatMap { type ->
            typeWeaknessMap[type.lowercase()] ?: emptyList()
        }.distinct() // Remove duplicates
    }
}
