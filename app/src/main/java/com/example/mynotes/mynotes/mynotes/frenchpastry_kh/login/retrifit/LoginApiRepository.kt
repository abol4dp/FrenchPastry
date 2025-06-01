package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.model.SendCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.model.VerifyCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.model.homemodel.HomeResponse
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


suspend fun getMain(){
val response = try {

    apiService.getMain()

}catch (e : Exception){
    return
}
if (response.isSuccessful){
val body = response.body()
    body.let {
        main.let { it }

    }


}






}





}