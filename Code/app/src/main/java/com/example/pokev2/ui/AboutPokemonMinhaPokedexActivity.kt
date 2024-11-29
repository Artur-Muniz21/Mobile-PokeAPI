package com.example.pokev2.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokev2.R
import com.example.pokev2.utils.CapturedPokemonManager


class AboutPokemonMinhaPokedexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_pokemon_minha_pokedex)
        Log.d("AboutPokemon", "Activity started")
            ?: "Pokemon sem id"
        val pokemonName = intent.getStringExtra("pokemonName") ?: "Pokémon Desconhecido"
        val pokemonImage = intent.getStringExtra("pokemonImage") ?: "Pokemon sem imagem"
        val pokemonHeight = intent.getStringExtra("pokemonHeight") ?: "Pokemon sem altura"
        val pokemonDescription = intent.getStringExtra("pokemonDescription") ?: "Pokemon sem descrição"
        val pokemonWeight = intent.getStringExtra("pokemonWeight") ?: "Pokemon sem peso"

        val pokemonNameTextView: TextView = findViewById(R.id.pokemonNameTextView)
        val pokemonImageView: ImageView = findViewById(R.id.pokemonImageView)
        val pokemonHeightTextView: TextView = findViewById(R.id.pokemonHeightTextView)
        val pokemonDescriptionTextView: TextView = findViewById(R.id.pokemonDescriptionTextView)
        val pokemonWeightTextView: TextView = findViewById(R.id.pokemonWeightTextView)

        pokemonNameTextView.text = pokemonName
        pokemonHeightTextView.text = pokemonHeight
        pokemonDescriptionTextView.text = pokemonDescription
        pokemonWeightTextView.text = pokemonWeight

        Glide.with(this)
            .load(pokemonImage)
            .placeholder(R.drawable.placeholder_image)
            .into(pokemonImageView)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

    }
}
