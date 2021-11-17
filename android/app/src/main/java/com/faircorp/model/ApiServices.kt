package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val API_url = "http://boussejra.amir.faircorp.cleverapps.io/api/"
    //"https://dev-mind.fr/training/android/"
    //"http://ye.wenjing.cleverapps.io/api/"
    //
    //"https://faircorp-yahya-mouman.cleverapps.io/faircorp/"

    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(API_url)
            .build()
            .create(WindowApiService::class.java)
    }

    val roomsApiService : RoomApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(API_url)
            .build()
            .create(RoomApiService::class.java)
    }
}