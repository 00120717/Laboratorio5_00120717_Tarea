package com.mramirez.mypokedex.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mramirez.mypokedex.AppConstants
import com.mramirez.mypokedex.MyPokemonAdapter
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.adapters.PokemonAdapter
import com.mramirez.mypokedex.adapters.PokemonSimpleListAdapter
import com.mramirez.mypokedex.models.Pokemon
import kotlinx.android.synthetic.main.pokemons_list_fragment.*
import kotlinx.android.synthetic.main.pokemons_list_fragment.view.*

class MainListFragment: Fragment(){

    private lateinit var  pokemons :ArrayList<Pokemon>
    private lateinit var pokemonsAdapter : MyPokemonAdapter
    var listenerTool :  SearchNewMovieListener? = null

    companion object {
        fun newInstance(dataset : ArrayList<Pokemon>): MainListFragment{
            val newFragment = MainListFragment()
            newFragment.pokemons = dataset
            return newFragment
        }
    }

    interface SearchNewMovieListener{
        fun searchPokemon(pokemonName: String)

        fun managePortraitItemClick(pokemon: Pokemon)

        fun manageLandscapeItemClick(pokemon: Pokemon)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.pokemons_list_fragment, container, false)

        if(savedInstanceState != null) pokemons = savedInstanceState.getParcelableArrayList<Pokemon>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container: View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            pokemonsAdapter = PokemonAdapter(pokemons, {pokemon:Pokemon->listenerTool?.managePortraitItemClick(pokemon)})
            container.pokemon_list_rv.adapter = pokemonsAdapter as PokemonAdapter
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            pokemonsAdapter = PokemonSimpleListAdapter(pokemons, {pokemon:Pokemon->listenerTool?.manageLandscapeItemClick(pokemon)})
            container.pokemon_list_rv.adapter = pokemonsAdapter as PokemonSimpleListAdapter
        }

        container.pokemon_list_rv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    fun initSearchButton(container: View) = container.add_movie_btn.setOnClickListener {
        listenerTool?.searchPokemon(pokemon_name_et.text.toString())
    }

    fun updatePokemonsAdapter(pokemonList: ArrayList<Pokemon>){ pokemonsAdapter.changeDataSet(pokemonList) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SearchNewMovieListener) {
            listenerTool = context
        } else {
            throw RuntimeException("Se necesita una implementación de  la interfaz")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY, pokemons)
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerTool = null
    }
}