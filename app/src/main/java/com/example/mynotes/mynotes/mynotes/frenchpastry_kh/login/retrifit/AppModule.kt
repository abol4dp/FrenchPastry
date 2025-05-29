package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private const val URL = "https://pastry.alirezaahmadi.info/api/v1/"

    @Provides
    @Singleton
fun moduleRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()



}
