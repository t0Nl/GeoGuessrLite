package com.example.android.geoguessrlite.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://geoguessr.com/api/v3/social/maps/browse/popular/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GuessLocationsApiService {
    @GET("random")
    suspend fun getProperties(): List<Location>
}

object GuessLocationsApi {
    val retrofitService: GuessLocationsApiService by lazy { retrofit.create(GuessLocationsApiService::class.java) }
}
