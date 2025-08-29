package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext

sealed class SealedClassNavName(val Route:String) {
    data object Login : SealedClassNavName("loginscreen")
    data object Home : SealedClassNavName("homescreen")
    data object Alert : SealedClassNavName("alertdialog")



}