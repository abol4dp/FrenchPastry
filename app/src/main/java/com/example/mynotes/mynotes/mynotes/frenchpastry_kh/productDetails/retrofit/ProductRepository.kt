package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.retrofit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.Resource
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val service: ProductService
) {
    suspend fun getProductById(id: Int): Resource<ProductResponse> {
        return try {
            val response = service.getProductById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("پاسخ خالی از سرور")
            } else {
                Resource.Error("خطا از سرور: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("خطا در اتصال: ${e.message}")
        }
    }
}
