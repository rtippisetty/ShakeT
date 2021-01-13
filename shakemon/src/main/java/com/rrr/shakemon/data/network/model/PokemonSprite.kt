package com.rrr.shakemon.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * pokemon n/w model that holds its sprite in one of its fields
 */
data class PokemonSprite(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("sprites")
    val sprite: Sprite

) {
    inner class Sprite(
        @SerializedName("front_default")
        val frontDefault: String,
        @SerializedName("other")
        val other: Other
    )

    inner class Other(
        @SerializedName("official-artwork")
        val officialArtwork: OfficialArtwork?
    )

    inner class OfficialArtwork(
        @SerializedName("front_default")
        val frontDefault: String?
    )
}