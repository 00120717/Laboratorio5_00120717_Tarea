package com.mramirez.mypokedex.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mramirez.mypokedex.MyMovieAdapter
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.models.Pokemon
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonSimpleListAdapter(var pokemons:List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonSimpleListAdapter.ViewHolder>(),
    MyMovieAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) =holder.bind(pokemons[pos], clickListener)

    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.pokemons = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(pokemon: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView){
            title_list_item.text=pokemon.name
            type1_list_item.text=pokemon.fsttype
            type2_list_item.text=pokemon.sndtype
            this.setOnClickListener { clickListener(pokemon) }
        }
    }
}