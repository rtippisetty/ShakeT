package com.rrr.shakemon

import android.app.Activity
import android.content.Intent
import com.rrr.shakemon.data.PokemonRepository
import com.rrr.shakemon.ui.ShakemonActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.util.*

/**
 * Main SDK interface for applciations
 */
class ShakemonManager private constructor() {

    companion object {
        val INSTANCE: ShakemonManager = ShakemonManager()
    }
    private val pokemonDataManager = PokemonRepository()

    /**
     * Service that provide Shakespeare translations for a Pokemon description
     * @param pokemonName pokemon species name
     * @param callBack translated text returns via user passed callback
     */
    fun getShakespeareText(pokemonName: String, callBack: IServiceCallback) {
        if(pokemonName.isEmpty()) {
            callBack.error(Exception("Unknown name"))
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val translatedText = pokemonDataManager.getPokemonShakespeareDescription(pokemonName.toLowerCase(
                    Locale.US))
                CoroutineScope(Dispatchers.Main).launch {
                    callBack.success(translatedText)
                }
            } catch (exception: HttpException) {
                val libException = exception
                callBack.error(libException)
            } catch (exception: Exception) {
                callBack.error(exception)
            }
        }
    }

    /**
     * Service that provide Pokemon's sprite for a given Pokemon
     * @param pokemonName pokemon species name
     * @param callBack Pokemon's sprite image like returns via user passed callback
     */
    fun getPokemonSprite(pokemonName: String, callBack: IServiceCallback) {
        if(pokemonName.isEmpty()) {
            callBack.error(Exception("Unknown name"))
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemonSprite =
                    pokemonDataManager.getPokemonSprite(pokemonName.toLowerCase(Locale.US))
                CoroutineScope(Dispatchers.Main).launch {
                    callBack.success(pokemonSprite)
                }
            } catch (exception: HttpException) {
                val libException = exception
                callBack.error(libException)
            } catch (exception: Exception) {
                callBack.error(exception)
            }
        }
    }

    /**
     * Service that displays Pokemon's Shakspeare translated description and it's sprite using ui component
     * @param shakspeareText Shakspeare translated Text
     * @param pokemonSprite translated text returns via user passed callback
     */
    fun displayPokemon(activity: Activity,
                       shakspeareText: String,
                       pokemonSprite: String) {
        if(shakspeareText.isEmpty() || pokemonSprite.isEmpty()){
            return
        }

        activity.startActivity(
            Intent(activity, ShakemonActivity::class.java).apply {
                putExtras(ShakemonActivity.getArgBundle(
                    shakespeareText = shakspeareText,
                    pokemonSprite = pokemonSprite
                ))
            }
        )
    }
}