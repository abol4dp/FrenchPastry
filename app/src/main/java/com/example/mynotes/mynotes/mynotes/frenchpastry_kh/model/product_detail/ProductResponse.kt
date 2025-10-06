package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.product_detail

import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel.Pastry1


data class ProductResponse(
    val http_code: Int = 0,
    val message: String = "",
    val pastry: Pastry? = null,
    val success: Int = 0
)