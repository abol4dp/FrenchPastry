package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.retrofit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

@GET("v1/pastry/{id}")
suspend fun getProductById(
    @Path("id") id: Int
): Response<ProductResponse>

}