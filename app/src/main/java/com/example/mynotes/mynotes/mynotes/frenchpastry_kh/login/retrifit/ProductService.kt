package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
//details
    @GET("v1/pastryprod/{id}")
suspend fun getProductById(
    @Path("id") id:Int

): Response<ProductResponse>

}