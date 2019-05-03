package com.mramirez.mypokedex

import com.mramirez.mypokedex.models.Pokemon

object AppConstants{
    val dataset_saveinstance_key = "CLE"
    val MAIN_LIST_KEY = "key_list_pokemon"
}

interface MyPokemonAdapter {
    fun changeDataSet(newDataSet : List<Pokemon>)
}