package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel

data class Pastry(
    val ID: String="",
    val title: String="",
    val pastries: List<PastryItem> = emptyList(),
)