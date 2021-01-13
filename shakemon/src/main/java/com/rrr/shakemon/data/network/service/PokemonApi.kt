package com.rrr.shakemon.data.network.service

import com.rrr.shakemon.data.network.model.PokemonDescription
import com.rrr.shakemon.data.network.model.PokemonSprite
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Pokemon "pokeapi.co" api service
 */
interface PokemonApi {
    @GET
    suspend fun getPokemonDescription(@Url url: String): PokemonDescription

    @GET
    suspend fun getPokemonSprite(@Url url: String): PokemonSprite
}