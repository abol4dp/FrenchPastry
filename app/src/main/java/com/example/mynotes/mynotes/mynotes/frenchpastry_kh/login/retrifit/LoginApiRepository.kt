package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import android.content.Context
import android.util.Log
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.DeviceInfo
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.SendCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.VerifyCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LoginApiRepository @Inject constructor(
    private val apiService: LoginApiService










) {
    val main = MutableStateFlow<HomeResponse>(HomeResponse())
    val verifyCodeResponse = MutableStateFlow<VerifyCodeData>(VerifyCodeData())
    val sendCodResponse = MutableStateFlow<SendCodeData>(SendCodeData())
    val loading = MutableStateFlow(false)
    val errorVerifyCode = MutableStateFlow(false)


    suspend fun getMain() {
        val response = try {

            apiService.getMain()

        } catch (e: Exception) {
            return
        }
        if (response.isSuccessful) {
            val body = response.body()
            body.let {
                main.let { it }

            }

        }

    }


    suspend fun sendCodePhones(phone: String, context: Context): Result<SendCodeData> {

        return try {
            val response = apiService.sendCodePhone(
                phone = phone,
                deviceId = DeviceInfo.getDeviceID(context),
                publicId = DeviceInfo.getPublicKey(context)
            )

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

suspend fun verifyCode(code: String, phone: String, context: Context): Result<VerifyCodeData> {

    return try {
        val response = apiService.verifyCode(
            code = code,
            phone = phone,
            deviceId = DeviceInfo.getDeviceID(context),
            publicId = DeviceInfo.getPublicKey(context)
        )

        if (response.isSuccessful) {
            response.body()?.let { Result.success(it) } ?: Result.failure(Exception("پاسخ خالی"))
        } else {
            Result.failure(Exception("خطا: ${response.message()}"))
        }
    } catch (e: Exception) {

        Result.failure(Exception("خطا در اتصال: ${e.message}"))
    }
}






}

