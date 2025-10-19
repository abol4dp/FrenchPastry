package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.retrofit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.retrofit.HomeApiService
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.ProductResponse
import retrofit2.Response
import javax.inject.Inject
class ProductRepository @Inject constructor(
    private val service: ProductService
) {

    suspend fun getProductById(id: Int): Result<ProductResponse> {
        return try {
            val response = service.getProductById(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("پاسخ خالی"))
                }
            } else {
                Result.failure(Exception("خطا: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("خطا در اتصال: ${e.message}"))
        }
    }

}


