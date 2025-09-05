package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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


    val _loadingsenAlert = MutableStateFlow(false)
    val loadingAlert: StateFlow<Boolean> = _loadingsenAlert.asStateFlow()

    val errorVerifyCode = repository.errorVerifyCode
    var userPhone by mutableStateOf("  ")

    private val _sendCodeStatus = MutableStateFlow(SendCodeStatus.Idle)
    val sendCodeStatus: StateFlow<SendCodeStatus> = _sendCodeStatus


    enum class VerifyStatus { Idle, Success, Failure }
    enum class SendCodeStatus { Idle, Success, Failure }

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
            userPhone = phone
            _loadingsen.emit(true)
            _errorMessage.emit(null)
            Log.d("LOGIN/VM", "sending code | phone=$phone")

            val result = repository.sendCodePhones(phone, context)
            result.onSuccess { response: SendCodeData ->
                Log.d(
                    "LOGIN/VM",
                    "sendCode SUCCESS -> success=${response.success}, msg=${response.message}, http=${response.http_code}, expireAt=${response.expire_at}, seconds=${response.seconds}"
                )

                if (response.success == 1) {
                    _sendCodeStatus.emit(SendCodeStatus.Success)
                } else
                    _sendCodeStatus.emit(SendCodeStatus.Failure)

            }.onFailure {


            }
            _loadingsen.emit(false)


        }


    }

    fun verifyCode(code: String, phone: String, context: Context) {
        viewModelScope.launch {
            _loadingver.emit(true)
            _errorMessage.emit(null)

            Log.d("LOGIN/VM", "verifyCode() called | code=$code | phone=$phone")

            val result = repository.verifyCode(code, phone, context)

            result.onSuccess { response ->
                Log.d("LOGIN/VM", "verifyCode() success | response=$response | response.success=${response.success}")

                if (response.success == 1) {
                    _verifyStatus.emit(VerifyStatus.Success)
                    Log.d("LOGIN/VM", "verifyCode -> Success")
                } else {
                    _verifyStatus.emit(VerifyStatus.Failure)
                    Log.e("LOGIN/VM", "verifyCode -> Failure (server returned success=${response.success})")
                }

            }.onFailure { exception ->
                Log.e("LOGIN/VM", "verifyCode() exception: ${exception.message}", exception)
                _verifyStatus.emit(VerifyStatus.Failure)
            }

            _loadingver.emit(false)
        }
    }


}





