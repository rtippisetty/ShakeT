package com.rrr.shakemon.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * pokemon-species n/w model class that holds flavor_text_entries description
 */
data class PokemonDescription(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>

) {
    inner class FlavorTextEntry(
        @SerializedName("flavor_text")
        val flavorText: String,

        @SerializedName("language")
        val language: Detail,

        @SerializedName("version")
        val version: Detail
    )

    inner class Detail(
        @SerializedName("name")
        val name: String,

        @SerializedName("url")
        val url: String
    )
}