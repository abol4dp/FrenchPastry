package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.LoginApiRepository
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.SendCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.VerifyCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class VerifyStatus { Idle, Success, Failure }

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val repository: LoginApiRepository

) : ViewModel() {


    private val _mainResponse = MutableStateFlow<HomeResponse>(HomeResponse())
    val mainResponse: StateFlow<HomeResponse> = _mainResponse.asStateFlow()


    private val _sendCode = MutableStateFlow<SendCodeData>(SendCodeData())
    val sendCode: StateFlow<SendCodeData> = _sendCode.asStateFlow()


    private val _verifyCode = MutableStateFlow<VerifyCodeData>(VerifyCodeData())
    val verifyCode: StateFlow<VerifyCodeData> = _verifyCode.asStateFlow()

    private val _loadingver = MutableStateFlow(false)
    val loadingver: StateFlow<Boolean> = _loadingver.asStateFlow()

    private val _loadingsen = MutableStateFlow(false)
    val loadingsen: StateFlow<Boolean> = _loadingsen.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _verifyStatus = MutableStateFlow(VerifyStatus.Idle)
    val verifyStatus: StateFlow<VerifyStatus> = _verifyStatus

    enum class VerifyStatus { Idle, Success, Failure }

    fun getMain() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMain()
        }

    }

    fun resetVerifyStatus() {
        _verifyStatus.value = VerifyStatus.Idle
    }

    fun sendCodeNumber(phone: String, context: Context) {
        viewModelScope.launch {
            _loadingsen.emit(true)
            _errorMessage.emit(null)
            Log.d("LOGIN/VM", "sending code | phone=$phone")

            val result = repository.sendCodePhones(phone, context)
            result.onSuccess { response: SendCodeData ->
                Log.d(
                    "LOGIN/VM",
                    "sendCode SUCCESS -> success=${response.success}, msg=${response.message}, http=${response.http_code}, expireAt=${response.expire_at}, seconds=${response.seconds}"
                )
                _sendCode.emit(response)
                if (response.success != 1) {
                    _errorMessage.emit(response.message)
                }
            }.onFailure { exception ->
                Log.e("LOGIN/VM", "sendCode FAILURE -> ${exception.message}", exception)
                _errorMessage.emit(exception.message ?: "خطا در ارسال کد")
            }
            _loadingsen.emit(false)


        }


    }


    fun verifyCode(code: String, phone: String, context: Context) {
        viewModelScope.launch {
            _loadingver.emit(true)
            _errorMessage.emit(null)
            val result = repository.verifyCode(code, phone, context)

            result.onSuccess { response ->

                if (response.success == 1) {
                    _verifyStatus.emit(VerifyStatus.Success)

                } else
                    _verifyStatus.emit(VerifyStatus.Failure)


            }.onFailure { exception ->


            }
            _loadingver.emit(false)


        }


    }

}





