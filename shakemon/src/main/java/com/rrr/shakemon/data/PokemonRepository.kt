package com.rrr.shakemon.data

import android.util.Log
import androidx.annotation.RestrictTo
import com.rrr.shakemon.data.network.RetrofitModule
import com.rrr.shakemon.data.network.model.PokemonDescription
import com.rrr.shakemon.data.network.service.PokemonApi
import com.rrr.shakemon.data.network.service.TranslationsApi
import java.lang.Exception

/**
 * Repository class to manage network and location repositories
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class PokemonRepository(
    private val pokemonApi: PokemonApi = RetrofitModule.servicePokemonApi(),
    private val translationsApi: TranslationsApi = RetrofitModule.serviceFunTranslationsApi()) {

    private val TAG: String = PokemonRepository::class.java.simpleName

    /**
     * Method fetches pokemon description first and then gets corresponding Shakespeare text
     * @param name : Pokemon name to search for the description
     */
    suspend fun getPokemonShakespeareDescription(name: String): String {
        Log.d(TAG, "Given Pokemon name : $name")
        try {
            val pokemonDescription = pokemonApi.getPokemonDescription(
                Util.POKEMON_DESCRIPTION_API + name
            )
            val flavourTextEntry: PokemonDescription.FlavorTextEntry? =
                pokemonDescription.flavorTextEntries.find { it.language.name == "en" }

            val flavourText = flavourTextEntry?.flavorText?.replace("\n", " ")
            Log.d(TAG, "Flavour text for $name : $flavourText")
            if(flavourText == null) throw Exception("No pokemon description found")

            return translationsApi.getShakespeareText(flavourText).contents.translated
        }catch (exception: Exception) {
            Log.d(TAG, "Unable to fetch Pokemon description due to ${exception}")
            throw exception
        }
    }

    /**
     * Method to fetch pokemon's sprite from ${pokemonApi} network service
     * @param name : Pokemon name to search for sprite
     */
    suspend fun getPokemonSprite(name: String): String {
        return try {
            val pokemonSprite = pokemonApi.getPokemonSprite(Util.POKEMON_SPRITES_API + name)
            pokemonSprite.sprite.other.officialArtwork?.frontDefault
                ?:pokemonSprite.sprite.frontDefault
        }catch (exception: Exception) {
            Log.d(TAG, "Unable to fetch Pokemon sprite due to ${exception}")
            throw exception
        }
    }
}