package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel


data class HomeResponse(
    val banners: List<Banner> = emptyList(),
    val http_code: Int = 0,
    val message: String = "",
    val pastries: List<Pastry> = emptyList(),
    val sliders: List<String> = emptyList(),
    val success: Int = 0
)