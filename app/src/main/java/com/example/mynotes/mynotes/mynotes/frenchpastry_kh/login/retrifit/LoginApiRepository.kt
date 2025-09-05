package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import android.content.Context
import android.util.Log
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.DeviceInfo
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.SendCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.VerifyCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LoginApiRepository @Inject constructor(
    private val apiService: LoginApiService,

) {
    val errorVerifyCode =MutableStateFlow(false)

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


    suspend fun sendCodePhones(phone: String, context: Context): Result<SendCodeData> {

        return try {
            val deviceId = DeviceInfo.getDeviceID(context)
            val publicId = DeviceInfo.getPublicKey(context)

            Log.d(
                "LOGIN/REPO",
                "sendCodePhones -> phone=$phone, deviceId=$deviceId, publicKey=$publicId"
            )

            val response = apiService.sendCodePhone(
                phone = phone,
                deviceId = DeviceInfo.getDeviceID(context),
                publicId = DeviceInfo.getPublicKey(context)
            )
            Log.d(
                "LOGIN/REPO",
                "HTTP sendCode -> code=${response.code()}, message=${response.message()}"
            )

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("LOGIN/REPO", "HTTP body=$body")
                body?.let { Result.success(it) }
                    ?: Result.failure(Exception("پاسخ خالی"))
            }else {
                val err = response.errorBody()?.string()
                Log.e("LOGIN/REPO", "HTTP ERROR ${response.code()} | ${response.message()} | body=$err") // LOG
                Result.failure(Exception("HTTP ${response.code()} - ${response.message()} - $err"))
            }
        } catch (e: Exception) {
            Log.e("LOGIN/REPO", "EXCEPTION -> ${e.message}", e)
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
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("پاسخ خالی"))
            } else {

                val errorBody = response.errorBody()?.string()
                val gson = Gson()
                val errorResponse = try {
                    gson.fromJson(errorBody, VerifyCodeData::class.java)
                } catch (e: Exception) {
                    VerifyCodeData(success = 0, message = "خطای ناشناخته از سرور")
                }
                Result.success(errorResponse)

            }


        } catch (e: Exception) {

            Result.failure(Exception("خطا در اتصال: ${e.message}"))
        }
    }


}

