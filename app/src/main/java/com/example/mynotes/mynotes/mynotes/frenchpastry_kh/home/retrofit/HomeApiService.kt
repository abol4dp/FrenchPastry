package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.retrofit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiService {

    @GET("v1/main")
    suspend fun getMain(): Response<HomeResponse>


}