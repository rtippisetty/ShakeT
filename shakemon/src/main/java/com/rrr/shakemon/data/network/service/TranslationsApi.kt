package com.rrr.shakemon.data.network.service

import com.rrr.shakemon.data.network.model.Translation
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * "api.funtranslations.com" api service for shakespearean translations
 */
interface TranslationsApi {
    @GET("translate/shakespeare.json")
    suspend fun getShakespeareText(@Query("text")text: String): Translation
}