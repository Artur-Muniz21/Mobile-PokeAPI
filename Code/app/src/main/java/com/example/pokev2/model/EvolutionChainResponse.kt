package com.example.pokev2.model

data class EvolutionChainResponse(
    val chain: EvolutionChain
)

data class EvolutionChain(
    val species: Species,
    val evolves_to: List<EvolutionChain>
)

data class Species(
    val name: String,
    val url: String
)
