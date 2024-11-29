import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.AboutPokemonActivity
import com.example.pokev2.R
import com.example.pokev2.adapter.PokemonAdapter
import com.example.pokev2.api.PokeApiService
import com.example.pokev2.model.Pokemon
import com.example.pokev2.model.PokemonListResponse
import com.example.pokev2.ui.AboutPokemonMinhaPokedexActivity
import com.example.pokev2.ui.MinhaPokedexActivity
import com.example.pokev2.utils.RetrofitClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private lateinit var pokeApiService: PokeApiService
    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setupRecyclerView()
        setupRetrofit()
        fetchPokemonData() // Fetch Pokémon data

        val addButton: ImageButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            onAddButtonClick()
        }
    }

    private fun onAddButtonClick() {
        val intent = Intent(this, MinhaPokedexActivity::class.java)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.pokemonRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 colunas por grid
    }

    private fun setupRetrofit() {
        val baseUrl = "https://pokeapi.co/api/v2/"
        val gson: Gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        pokeApiService = retrofit.create(PokeApiService::class.java)
    }

    private fun fetchPokemonData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemonListResponse: PokemonListResponse = RetrofitClient.pokeApiService.getPokemons()
                val pokemonSummaries = pokemonListResponse.results

                for (pokemonSummary in pokemonSummaries) {
                    val game_index = pokemonSummary.url.split("/".toRegex()).dropLast(1).last().toInt()
                    val pokemonResponse = RetrofitClient.pokeApiService.getPokemon(game_index)

                    val types = pokemonResponse.types.map { it.type.name } // Extract types

                    // Fetch Pokémon species for the description
                    val speciesResponse = RetrofitClient.pokeApiService.getPokemonSpecies(game_index)
                    val description = speciesResponse.flavor_text_entries
                        .firstOrNull { it.language.name == "en" }
                        ?.flavor_text?.replace("\n", " ")
                        ?: "No description available."


                    val pokemon = Pokemon(
                        game_index = game_index,
                        name = pokemonResponse.name.capitalize(),
                        imageUrl = pokemonResponse.sprites.front_default ?: "",
                        types = pokemonResponse.types.map { it.type.name },
                        height = "${pokemonResponse.height / 10.0}m", // Convert decimeters to meters
                        weight = "${pokemonResponse.weight / 10.0}kg", // Convert hectograms to kilograms
                        gender = "♂ 87.5%, ♀ 12.5%", // Placeholder logic
                        base_experience = pokemonResponse.base_experience ?: 0,
                        xDescription = description
                    )
                    pokemonList.add(pokemon)
                }

                withContext(Dispatchers.Main) {
                    adapter = PokemonAdapter(pokemonList)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("PokemonActivity", "Error fetching data", e)
            }
        }
    }


}
