package com.mramirez.mypokedex.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.mramirez.mypokedex.AppConstants
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.fragments.MainContentFragment
import com.mramirez.mypokedex.fragments.MainListFragment
import com.mramirez.mypokedex.models.Pokemon
import com.mramirez.mypokedex.network.NetworkUtils
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), MainListFragment.SearchNewMovieListener {
    private lateinit var mainFragment : MainListFragment
    private lateinit var mainContentFragment: MainContentFragment

    private var pokemonList = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pokemonList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_saveinstance_key) ?: ArrayList()

        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_saveinstance_key, pokemonList)
        super.onSaveInstanceState(outState)
    }

    fun initMainFragment(){
        mainFragment = MainListFragment.newInstance(pokemonList)

        val resource = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment
        else {
            mainContentFragment = MainContentFragment.newInstance(Pokemon())
            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

            R.id.land_main_fragment
        }

        changeFragment(resource, mainFragment)
    }

    fun addPokemonToList(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        mainFragment.updatePokemonsAdapter(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }

    override fun searchPokemon(pokemonName: String) {
        FetchPokemon().execute(pokemonName)
    }

    override fun managePortraitItemClick(pokemon: Pokemon) {
        val pokemonBundle = Bundle()
        pokemonBundle.putParcelable("POKEMON", pokemon)
        startActivity(Intent(this, PokemonViewerActivity::class.java).putExtras(pokemonBundle))
    }

    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }

    override fun manageLandscapeItemClick(pokemon: Pokemon) {
        mainContentFragment = MainContentFragment.newInstance(pokemon)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    private inner class FetchPokemon : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {

            if (params.isNullOrEmpty()) return ""

            val pokemonName = params[0]
            val pokemonUrl = NetworkUtils().buildtSearchUrl("type",pokemonName)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokemonUrl)
            } catch (e: IOException) {
                ""
            }
        }

        override fun onPostExecute(pokemonInfo: String) {
            val pokemon = if (!pokemonInfo.isEmpty()) {
                val root = JSONObject(pokemonInfo)
                val results = root.getJSONArray("pokemon")
                MutableList(20) { i ->
                    val resulty = JSONObject(results[i].toString())
                    val result = JSONObject(resulty.getString("pokemon"))

                    addPokemonToList(
                        Pokemon(i,
                            result.getString("name").capitalize(),
                            R.string.n_a_value.toString(),
                            R.string.n_a_value.toString(),
                            R.string.n_a_value.toString(),
                            R.string.n_a_value.toString(),
                            result.getString("url"),
                            R.string.n_a_value.toString())
                    )
                }
            } else {
                MutableList(20) { i ->
                    Pokemon(i, R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
                }
            }

        }
    }
}

