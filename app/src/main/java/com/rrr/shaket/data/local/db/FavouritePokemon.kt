package com.rrr.shaket.data.local.db
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rrr.shaket.data.local.model.Pokemon

/**
 * Table data entry to hold favourite pokemon
 */
@Entity(tableName = "favourite_table")
data class FavouritePokemon(
    @PrimaryKey val name: String,
    val sprite: String,
    val shakespeareText: String
) {
    constructor(pokemon: Pokemon): this(
                name = pokemon.name,
                sprite = pokemon.pokemonSprite,
                shakespeareText = pokemon.shakespeareDescription
            )

    override fun equals(other: Any?): Boolean {
        return other is FavouritePokemon
                && other.name == name
                && other.sprite == sprite
                && other.shakespeareText == shakespeareText
    }
}