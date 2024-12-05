package com.example.pokev2.utils

import com.example.pokev2.model.Pokemon
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object CapturedPokemonManager {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("capturedPokemon")

    // Adicionar Pokémon ao Realtime Database
    fun addPokemon(pokemon: Pokemon, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val pokemonId = pokemon.game_index.toString() // Usando game_index como chave única
        database.child(pokemonId)
            .setValue(pokemon)
            .addOnSuccessListener {
                onSuccess() // Chama callback de sucesso
            }
            .addOnFailureListener { exception ->
                onFailure(exception) // Chama callback de erro
            }
    }

    // Obter a lista de Pokémons capturados
    fun getCapturedPokemon(onSuccess: (List<Pokemon>) -> Unit, onFailure: (Exception) -> Unit) {
        database.get()
            .addOnSuccessListener { snapshot ->
                val pokemonList = snapshot.children.mapNotNull { it.getValue(Pokemon::class.java) }
                onSuccess(pokemonList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Atualizar o nome de um Pokémon pelo ID
    fun updatePokemonName(pokemonId: Int, newName: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val pokemonIdStr = pokemonId.toString()
        database.child(pokemonIdStr).child("name") .setValue(newName)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                exception -> onFailure(exception)
            }
    }


    // Remover um Pokémon do Realtime Database pelo ID
    fun libertaPokemon(pokemonId: Int, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val pokemonIdStr = pokemonId.toString()
        database.child(pokemonIdStr)
            .removeValue()
            .addOnSuccessListener {
                onSuccess()
                }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }

    }
}