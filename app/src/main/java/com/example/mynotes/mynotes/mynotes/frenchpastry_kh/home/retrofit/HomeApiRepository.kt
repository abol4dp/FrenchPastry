package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.retrofit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import javax.inject.Inject

class HomeApiRepository @Inject constructor(
    private val apiService: HomeApiService

) {


    suspend fun getMain(): Result<HomeResponse> {
        return try {
            val response = apiService.getMain()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("پاسخ خالی"))

            } else {
                Result.failure(Exception("خطا: ${response.message()}"))

            }
        } catch (e: Exception) {
            Result.failure(Exception("خطا در اتصال: ${e.message}"))

        }
    }

}