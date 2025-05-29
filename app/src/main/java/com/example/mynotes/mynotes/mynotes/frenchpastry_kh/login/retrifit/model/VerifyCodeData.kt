package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.model

data class VerifyCodeData(
    val api: String="",
    val http_code: Int=0,
    val message: String="",
    val success: Int=0,
    val user: User=User()
)