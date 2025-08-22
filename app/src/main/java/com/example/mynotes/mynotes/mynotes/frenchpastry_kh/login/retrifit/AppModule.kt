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


    private const val URL = "https://pastry.alirezaahmadi.info/api/"
    var API_KEY = ""

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideRetrofitApiService(retrofit: Retrofit): LoginApiService =
        retrofit.create(LoginApiService::class.java)

}
