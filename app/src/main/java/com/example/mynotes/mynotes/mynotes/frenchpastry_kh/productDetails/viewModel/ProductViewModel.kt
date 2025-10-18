package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.retrofit.ProductService
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.Pastry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val service: ProductService
) : ViewModel() {

    private val _product = MutableStateFlow<Pastry?>(null)
    val product: StateFlow<Pastry?> = _product

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error



    fun loadProduct(id: Int) {
        Log.d("DETAILS/VM", "loadProduct called with id=$id")
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = service.getProductById(id)
                Log.d("DETAILS/VM", "API Raw Response: ${response.raw()}")
                Log.d("DETAILS/VM", "API Headers: ${response.headers()}")

                if (response.isSuccessful) {
                    Log.d("DETAILS/VM", "Full body: ${response.body()}")
                    _product.value = response.body()?.pastry
                    Log.d("DETAILS/VM", "Parsed product: ${_product.value}")
                } else {
                    Log.e(
                        "DETAILS/VM",
                        "API Failed -> code=${response.code()} | message=${response.message()}"
                    )
                    _error.value = "خطا در دریافت اطلاعات"
                }
            } catch (e: Exception) {
                Log.e("DETAILS/VM", "Exception -> ${e.localizedMessage}", e)
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }

    }
}





