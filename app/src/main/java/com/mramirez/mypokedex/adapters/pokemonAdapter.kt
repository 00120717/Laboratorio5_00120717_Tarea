package com.mramirez.mypokedex.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mramirez.mypokedex.MyPokemonAdapter
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.models.Pokemon
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonAdapter(var pokemons: List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(),
    MyPokemonAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(pokemons[position], clickListener)

    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.pokemons = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView){
           title_list_item.text = item.name
           type1_list_item.text = item.fsttype
            type2_list_item.text = item.sndtype
            this.setOnClickListener { clickListener(item) }
        }
    }
}