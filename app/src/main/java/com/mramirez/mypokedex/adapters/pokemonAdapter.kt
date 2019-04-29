package com.mramirez.mypokedex.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mramirez.mypokedex.MyMovieAdapter
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.models.Pokemon
import kotlinx.android.synthetic.main.cardview_pokemon.view.*

class PokemonAdapter(var pokemons: List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(),
    MyMovieAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_pokemon, parent, false)
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
            Glide.with(itemView.context)
                .load(item.url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(pokemon_image_cv)
            pokemon_name_cv.text = item.name
            type1_cv.text = item.fsttype
            type2_cv.text = item.sndtype
            weight_cv.text = item.weight
            height_cv.text = item.height
            sprites_cv.text = item.sprite
            this.setOnClickListener { clickListener(item) }
        }
    }
}