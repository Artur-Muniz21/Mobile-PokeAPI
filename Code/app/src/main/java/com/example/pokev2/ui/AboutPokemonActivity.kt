package com.example.pokev2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.pokev2.model.EvolutionChain
import com.example.pokev2.model.Pokemon
import com.example.pokev2.model.Species
import com.example.pokev2.utils.CapturedPokemonManager
import com.example.pokev2.utils.RetrofitClient
import com.example.pokev2.utils.TypeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutPokemonActivity : AppCompatActivity() {

    private lateinit var backgroundView: View
    private lateinit var pokemonImageView: ImageView
    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonIdTextView: TextView
    private lateinit var pokemonHeightTextView: TextView
    private lateinit var pokemonWeightTextView: TextView
    private lateinit var pokemonbase_experienceTextView: TextView
    private lateinit var pokemonDescriptionTextView: TextView
    private lateinit var weaknessContainer: LinearLayout
    private lateinit var evolutionContainer: LinearLayout
    private lateinit var evoluirButton: View
    private lateinit var capturarButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_pokemon)

        // Initialize views
        backgroundView = findViewById(R.id.backgroundDesign)
        pokemonImageView = findViewById(R.id.pokemonImageView)
        pokemonNameTextView = findViewById(R.id.pokemonNameTextView)
        pokemonIdTextView = findViewById(R.id.pokemonIdTextView)
        pokemonHeightTextView = findViewById(R.id.textViewHeight)
        pokemonWeightTextView = findViewById(R.id.textViewWeight)
        pokemonbase_experienceTextView = findViewById(R.id.textViewbase_experience)
        pokemonDescriptionTextView = findViewById(R.id.textViewDescription)
        weaknessContainer = findViewById(R.id.weaknessContainer)
        evolutionContainer = findViewById(R.id.evolutionContainer)
        evoluirButton = findViewById(R.id.buttonEvoluir)
        capturarButton = findViewById(R.id.buttonCapturar)

        // Retrieve data from Intent
        val pokemonName = intent.getStringExtra("pokemonName") ?: "Unknown"
        val pokemonId = intent.getIntExtra("pokemonId", -1)
        val pokemonImage = intent.getStringExtra("pokemonImage") ?: ""
        val pokemonTypes = intent.getStringArrayExtra("pokemonTypes")?.toList() ?: listOf()
        val pokemonHeight = intent.getStringExtra("pokemonHeight") ?: "Unknown"
        val pokemonWeight = intent.getStringExtra("pokemonWeight") ?: "Unknown"
        val pokemonbase_experience = intent.getStringExtra("pokemonbase_experience") ?: "Unknown"
        val pokemonDescription = intent.getStringExtra("pokemonDescription") ?: "No description available."
        val weaknesses = intent.getStringArrayExtra("pokemonWeaknesses")?.toList() ?: emptyList()
        val evolutionChainId = intent.getIntExtra("evolutionChainId", -1)

        // Apply background gradient
        val primaryType = pokemonTypes.firstOrNull() ?: "normal"
       // TypeUtils.getGradientForType(primaryType, backgroundView, this)

        // Populate views with data
        pokemonNameTextView.text = pokemonName
        pokemonIdTextView.text = String.format("#%03d", pokemonId)
        pokemonHeightTextView.text = pokemonHeight
        pokemonWeightTextView.text = pokemonWeight
        pokemonbase_experienceTextView.text = pokemonbase_experience
        pokemonDescriptionTextView.text = pokemonDescription

        // Load Pokémon image
        Glide.with(this)
            .load(pokemonImage)
            .placeholder(R.drawable.placeholder_image)
            .into(pokemonImageView)

        // Add weaknesses
        addWeaknessChips(weaknesses, weaknessContainer)

        // Fetch and display evolutions if available
        if (evolutionChainId != -1) {
            fetchAndDisplayEvolutions(evolutionChainId, evolutionContainer)
        }

        // Back Button Functionality
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Return to the previous activity
        }

        // Button logic
        evoluirButton.setOnClickListener {
            evolvePokemon(evolutionChainId)
        }

        capturarButton.setOnClickListener {
            val pokemon = Pokemon(
                game_index = pokemonIdTextView.text.toString().filter { it.isDigit() }.toInt(),
                name = pokemonNameTextView.text.toString(),
                imageUrl = pokemonImage,
                types = pokemonTypes,
                height = pokemonHeightTextView.text.toString(),
                weight = pokemonWeightTextView.text.toString(),
                base_experience = pokemonbase_experienceTextView.text.toString().toInt(),
                xDescription = pokemonDescriptionTextView.text.toString()
            )
            CapturedPokemonManager.addPokemon(
                pokemon,
                onSuccess = {
                    Toast.makeText(this, "${pokemon.name} capturado com sucesso!", Toast.LENGTH_SHORT).show()
                },
                onFailure = { exception ->
                    Toast.makeText(this, "Erro ao capturar ${pokemon.name}: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            )
            Toast.makeText(this, "${pokemon.name} capturado!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun evolvePokemon(evolutionChainId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (evolutionChainId == -1) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AboutPokemonActivity, "Nenhuma evolução disponivel", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val evolutionResponse = RetrofitClient.pokeApiService.getEvolutionChain(evolutionChainId)
                val currentName = pokemonNameTextView.text.toString().trim().lowercase()
                val nextEvolution = findNextEvolution(evolutionResponse.chain, currentName)

                withContext(Dispatchers.Main) {
                    if (nextEvolution == null) {
                        Toast.makeText(this@AboutPokemonActivity, "Esse pokemon não pode evoluir mais!", Toast.LENGTH_SHORT).show()
                    } else {
                        updateUIWithEvolution(nextEvolution)
                    }
                }
            } catch (e: Exception) {
                Log.e("AboutPokemonActivity", "Error ao evoluir o pokemon", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AboutPokemonActivity, "Error ao evoluir pokemon", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun findNextEvolution(chain: EvolutionChain, currentName: String): Species? {
        Log.d("AboutPokemonActivity", "Checking species: ${chain.species.name}")
        if (chain.species.name == currentName) {
            // Return the first available evolution if any
            val next = chain.evolves_to.firstOrNull()?.species
            Log.d("AboutPokemonActivity", "Next evolution: ${next?.name ?: "None"}")
            return next
        }
        // Recursively check in the evolves_to list
        for (evolution in chain.evolves_to) {
            val next = findNextEvolution(evolution, currentName)
            if (next != null) return next
        }
        Log.d("AboutPokemonActivity", "No further evolutions found for $currentName")
        return null
    }


    private fun updateUIWithEvolution(species: Species) {
        val evolvedId = species.url.split("/").dropLast(1).last().toInt()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val evolvedPokemon = RetrofitClient.pokeApiService.getPokemon(evolvedId)
                val descriptionResponse = RetrofitClient.pokeApiService.getPokemonSpecies(evolvedId)
                val description = descriptionResponse.flavor_text_entries
                    .firstOrNull { it.language.name == "en" }
                    ?.flavor_text?.replace("\n", " ") ?: "No description available."

                withContext(Dispatchers.Main) {
                    pokemonNameTextView.text = evolvedPokemon.name.capitalize()
                    pokemonIdTextView.text = String.format("#%03d", evolvedId)
                    pokemonHeightTextView.text = "${evolvedPokemon.height / 10.0}m"
                    pokemonWeightTextView.text = "${evolvedPokemon.weight / 10.0}kg"
                    pokemonbase_experienceTextView.text = evolvedPokemon.base_experience.toString()
                    pokemonDescriptionTextView.text = description

                    Glide.with(this@AboutPokemonActivity)
                        .load(evolvedPokemon.sprites.front_default)
                        .placeholder(R.drawable.placeholder_image)
                        .into(pokemonImageView)
                }
            } catch (e: Exception) {
                Log.e("AboutPokemonActivity", "Error updating evolved Pokémon UI", e)
            }
        }
    }

    private fun addWeaknessChips(weaknesses: List<String>, weaknessContainer: LinearLayout) {
        weaknesses.forEach { weakness ->
            val weaknessChip = TextView(this).apply {
                text = weakness.capitalize()
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setPadding(16, 8, 16, 8)
                background = ContextCompat.getDrawable(context, R.drawable.weakness_background)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 0, 8, 0)
                }
            }
            weaknessContainer.addView(weaknessChip)
        }
    }

    private fun fetchAndDisplayEvolutions(evolutionChainId: Int, evolutionContainer: LinearLayout) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.pokeApiService.getEvolutionChain(evolutionChainId)
                withContext(Dispatchers.Main) {
                    addEvolutions(response.chain, evolutionContainer)
                }
            } catch (e: Exception) {
                Log.e("AboutPokemonActivity", "Error fetching evolutions", e)
            }
        }
    }

    private fun addEvolutions(evolutionChain: EvolutionChain, evolutionContainer: LinearLayout) {
        fun addEvolutionToContainer(species: EvolutionChain) {
            val speciesId = species.species.url.split("/").dropLast(1).last().toInt()

            val evolutionImageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(200, 200).apply {
                    setMargins(16, 0, 16, 0)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP

                Glide.with(this@AboutPokemonActivity)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$speciesId.png")
                    .placeholder(R.drawable.placeholder_image)
                    .into(this)
            }

            evolutionContainer.addView(evolutionImageView)
            species.evolves_to.forEach { addEvolutionToContainer(it) }
        }

        addEvolutionToContainer(evolutionChain)
    }
}
