package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.SendCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.VerifyCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApiService {

    @GET("v1/main")
    suspend fun getMain(): Response<HomeResponse>



    @FormUrlEncoded
    @POST("v1/auth/phone/login")
    suspend fun sendCodePhone(
        @Header("app-device-uid")deviceId:String,
        @Header("app-public-key")publicId:String,
        @Field("phone") phone:String
    ):Response<SendCodeData>




    @FormUrlEncoded
    @POST("v1/auth/phone/login/verify")
    suspend fun verifyCode(
        @Header("app-device-uid")deviceId:String,
        @Header("app-public-key")publicId:String,
        @Field("code") code:String,
        @Field("phone") phone:String,
    ):Response<VerifyCodeData>

}
