package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.login

data class SendCodeData(
    val expire_at: String="",
    val http_code: Int=0,
    val message: String="خطا",
    val seconds: Int=0,
    val success: Int=0
)