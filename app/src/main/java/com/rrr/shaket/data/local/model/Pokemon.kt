package com.rrr.shaket.data.local.model

import com.rrr.shaket.data.local.db.FavouritePokemon

/**
 * Application specific view model to hold pokemon data
 */
data class Pokemon(
    var name: String = "",
    var shakespeareDescription: String = "",
    var pokemonSprite: String = "",
    var isFavourite: Boolean = false
) {
    constructor(favouritePokemon: FavouritePokemon): this(
        name = favouritePokemon.name,
        shakespeareDescription = favouritePokemon.shakespeareText,
        pokemonSprite = favouritePokemon.sprite,
        isFavourite = true
    )
    fun reset(){
        name = ""
        shakespeareDescription = ""
        pokemonSprite = ""
    }

    fun isEmpty() : Boolean =
        name.isEmpty() || shakespeareDescription.isEmpty() || pokemonSprite.isEmpty()
}