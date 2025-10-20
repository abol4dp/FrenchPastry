package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.Resource
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.retrofit.ProductService
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.Pastry
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail.ProductResponse
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.productDetails.retrofit.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productState = MutableStateFlow<Resource<ProductResponse>>(Resource.Loading)
    val productState: StateFlow<Resource<ProductResponse>> = _productState


    fun loadProduct(id: Int) {

        viewModelScope.launch {
            _productState.value = Resource.Loading
            _productState.value = repository.getProductById(id)
        }


    }

}





