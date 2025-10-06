package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.model.homemodel

data class Pastry1(
    val ID: String="",
    val title: String="",
    val pastries: List<PastryItem> = emptyList(),
)