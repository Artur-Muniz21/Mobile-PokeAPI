package com.example.pokev2.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.R
import com.example.pokev2.adapter.PokemonAdapter
import com.example.pokev2.utils.CapturedPokemonManager

class MinhaPokedexActivity : AppCompatActivity() {

    private lateinit var pokedexAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_pokedex)


        // Back Button Functionality
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Return to the previous activity
        }

        // Search Functionality
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val pokemonRecyclerView: RecyclerView = findViewById(R.id.pokemonRecyclerView)

        // Initialize Adapter with Captured Pokémon
        val capturedPokemon = CapturedPokemonManager.getCapturedPokemon()
        pokedexAdapter = PokemonAdapter(capturedPokemon)
        pokemonRecyclerView.adapter = pokedexAdapter
        pokemonRecyclerView.layoutManager = GridLayoutManager(this, 3)

        // Filter Pokémon List on Search
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = capturedPokemon.filter { it.name.contains(s.toString(), true) }
                pokedexAdapter = PokemonAdapter(filteredList)
                pokemonRecyclerView.adapter = pokedexAdapter
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
