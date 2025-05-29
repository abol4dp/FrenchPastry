package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.model.homemodel

data class Pastry(
    val ID: String="",
    val title: String="",
    val pastries: List<PastryItem> = emptyList(),
)