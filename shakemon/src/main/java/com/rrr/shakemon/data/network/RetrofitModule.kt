package com.rrr.shakemon.data.network

import androidx.annotation.RestrictTo
import com.rrr.shakemon.BuildConfig
import com.rrr.shakemon.data.Util
import com.rrr.shakemon.data.network.service.PokemonApi
import com.rrr.shakemon.data.network.service.TranslationsApi
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton Retrofit network calls wrapper
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
object RetrofitModule {
    private const val TIME_OUT = 60L
    private const val MAX_REQUESTS = 10
    private val dispatcher = Dispatcher().apply { maxRequestsPerHost = MAX_REQUESTS }

    fun servicePokemonApi(): PokemonApi =
        retrofit().create(PokemonApi::class.java)


    fun serviceFunTranslationsApi(): TranslationsApi =
        retrofit().create(TranslationsApi::class.java)


    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .client(okhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Util.SHAKESPEARE_TRANSLATION_API)
            .build()


    fun okhttpClient() = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(httpLoggingInterceptor())
        .dispatcher(dispatcher)
        .build()


    fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

}