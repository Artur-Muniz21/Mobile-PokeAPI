package com.example.pokev2.model

data class Pokemon(
    val game_index: Int = 0,
    var name: String = "",
    val imageUrl: String = "",
    val types: List<String> = emptyList(),
    val height: String = "",
    val weight: String = "",
    val gender: String = "Unknown",
    val base_experience: Int = 0,
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
) {

    constructor() : this(
        0, "", "", emptyList(), "", "", "Unknown", 0, "Unknown", "Unknown",
        0, "Unknown", emptyList(), "None", 0, "N/A", 0, 0, 0, 0, emptyList(), "N/A", "N/A"
    )
}
