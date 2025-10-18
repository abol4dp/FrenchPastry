package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.home.retrofit.HomeApiRepository
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.LoginApiRepository
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.HomeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeApiRepository,


    ) : ViewModel() {


    private val _mainResponse = MutableStateFlow<HomeResponse>(HomeResponse())
    val mainResponse: StateFlow<HomeResponse> = _mainResponse.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    fun getMain() {

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val result = repository.getMain()
            result.onSuccess { responce ->
                _mainResponse.value = responce
            }.onFailure { error ->

                println("خطا: ${error.message}")
            }
            _loading.value = false

        }


    }


}



