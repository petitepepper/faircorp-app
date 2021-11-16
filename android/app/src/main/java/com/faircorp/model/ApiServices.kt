package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("http://ye.wenjing.cleverapps.io/")
            .build()
            .create(WindowApiService::class.java)
    }
}