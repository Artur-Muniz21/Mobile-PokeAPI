package com.example.pokev2.utils

import com.example.pokev2.model.Pokemon

object CapturedPokemonManager {
    private val capturedPokemonList = mutableListOf<Pokemon>()

    fun addPokemon(pokemon: Pokemon) {
        capturedPokemonList.add(pokemon)
    }

    fun getCapturedPokemon(): List<Pokemon> {
        return capturedPokemonList
    }
}
