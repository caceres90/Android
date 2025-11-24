package com.sena.practica_de_consumo_api.di

import com.sena.practica_de_consumo_api.data.repository.PokemonApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Provides
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2"


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val Logging = HttpLoggingInterceptor()
        Logging.level = HttpLoggingInterceptor.Level.BODY
        return Logging
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        LoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetroFit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonApi(
        retrofit: Retrofit
    ): PokemonApi {
        return retrofit.create(PokemonApi::class.java)
    }
}