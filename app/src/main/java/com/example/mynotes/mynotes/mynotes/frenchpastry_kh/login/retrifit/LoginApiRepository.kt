package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import android.content.Context
import android.util.Log
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


    suspend fun sendCodePhone(phone: String, context: Context) {
        loading.emit(true)

        val response = try {
            apiService.sendCodePhone(
                phone = phone,
                deviceId = com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.DeviceInfo.getDeviceID(
                    context
                ),
                publicId = com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.DeviceInfo.getPublicKey(
                    context
                )
            )


        } catch (e: Exception) {
            Log.e("pasi", "senCodePhone error :${e.message.toString()}")
            return
        }


        if (response.isSuccessful) {
            loading.emit(false)
            val body = response.body()
            body?.let {
                sendCodResponse.emit(it)
            }
        } else {
            loading.emit(false)
            Log.e("pasi", "senCodePhone not succes ")

        }
    }


    suspend fun verifyCode(code: String, phone: String, context: Context) {

        loading.emit(true)

        val response = try {
            apiService.verifyCode(
                code = code,
                phone = phone,
                deviceId = com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.DeviceInfo.getDeviceID(
                    context
                ),
                publicId = com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.DeviceInfo.getPublicKey(
                    context
                )
            )


        } catch (e: Exception) {

            Log.e("pasi", "verifyCode error :${e.message.toString()}")
            return

        }

        if (response.isSuccessful) {
            loading.emit(false)
            val body = response.body()
            body?.let {
                verifyCodeResponse.emit(it)
            }
        } else {
            loading.emit(false)
            errorVerifyCode.emit(true)
            Log.e("pasi", "verifyCode not succes ")

        }


    }


}