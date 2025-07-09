package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel

import android.content.Context
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

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    fun getMain() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMain()
        }

    }


    fun sendCodeNumber(phone: String, context: Context) {
        viewModelScope.launch {
            _loading.emit(true)
            _errorMessage.emit(null)
            val result = repository.sendCodePhones(phone, context)
            result.onSuccess { response: SendCodeData ->
                _sendCode.emit(response)
                if (response.success != 1) {
                    _errorMessage.emit(response.message)
                }
            }.onFailure { exception ->
                _errorMessage.emit(exception.message ?: "خطا در ارسال کد")
            }
            _loading.emit(false)


        }


    }


    fun verifyCode(code: String, phone: String, context: Context) {
        viewModelScope.launch {
            _loading.emit(true)
            _errorMessage.emit(null)
            val result = repository.verifyCode(code, phone, context)

            result.onSuccess { response: VerifyCodeData ->
                _verifyCode.emit(response)
                if (response.success != 1) {
                    _errorMessage.emit(response.message)

                }


            }.onFailure { exception ->
                _errorMessage.emit(exception.message ?: "خطا در تایید کد")

            }
            _loading.emit(false)


        }


    }

}





