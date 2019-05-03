package com.mramirez.mypokedex.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.mramirez.mypokedex.R
import com.mramirez.mypokedex.models.Pokemon
import kotlinx.android.synthetic.main.main_content_fragment_layout.*
import kotlinx.android.synthetic.main.viewer_pokemon.*

class PokemonViewerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_pokemon)

        setSupportActionBar(toolbarviewer)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded)
        collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.Widget_Design_CollapsingToolbar)

        val reciever: Pokemon = intent?.extras?.getParcelable("POKEMON") ?: Pokemon()
        init(reciever)
    }

    fun init(pokemon: Pokemon){
        Glide.with(this)
            .load(pokemon.sprite)
            .placeholder(R.drawable.ic_launcher_background)
            .into(app_bar_image_viewer)
        collapsingtoolbarviewer.title = pokemon.name
        app_bar_id_viewer.text = pokemon.id.toString()
        type1_main_content_fragment.text = pokemon.fsttype
        type2_main_content_fragment.text = pokemon.sndtype
        weight_main_content_fragment.text = pokemon.weight
        height_main_content_fragment.text = pokemon.height

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}