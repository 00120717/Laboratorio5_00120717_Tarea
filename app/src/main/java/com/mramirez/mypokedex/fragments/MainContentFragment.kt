package com.mramirez.mypokedex.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.models.Pokemon
import kotlinx.android.synthetic.main.main_content_fragment_layout.view.*
import kotlinx.android.synthetic.main.viewer_pokemon.view.*

class MainContentFragment: Fragment() {

    var pokemon = Pokemon()

    companion object {
        fun newInstance(pokemon: Pokemon): MainContentFragment{
            val newFragment = MainContentFragment()
            newFragment.pokemon = pokemon
            return newFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.main_content_fragment_layout, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        view.pokemon_id_main_content_fragment.text = pokemon.id.toString()
        view.pokemon_name_main_content_fragment.text = pokemon.name
        view.type1_main_content_fragment.text = pokemon.fsttype
        view.type2_main_content_fragment.text = pokemon.sndtype
        view.weight_main_content_fragment.text = pokemon.weight
        view.height_main_content_fragment.text = pokemon.height

        Glide.with(view).load(pokemon.url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)
    }

}