package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.LoginApiRepository
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.SendCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.VerifyCodeData
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import dagger.hilt.android.HiltAndroidApp
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
    val _loading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    fun getMain() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMain()

        }

    }


}