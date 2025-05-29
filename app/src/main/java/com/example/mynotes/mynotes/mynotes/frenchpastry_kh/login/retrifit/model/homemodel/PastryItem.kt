package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.retrifit.model.homemodel

data class PastryItem(
    val ID: Int = 38,
    val active_stock: Boolean = false,
    val date: String = "",
    val date_l10n: String = "",
    val discount: String = "",
    val discount_percent: Int = 0,
    val discount_percent_l10n: String = "",
    val has_discount: Boolean = false,
    val min_order: Int = 0,
    val price: Long = 0L,
    val sale_price: Int = 0,
    val status: String = "",
    val stock: Int = 0,
    val thumbnail: String = "",
    val title: String = ""
)