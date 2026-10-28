package com.example.rubyeestofados

data class ProdutoApi(
    val id: Int,
    val nome: String,
    val preco: Double,
    val imagem: String? = null
)
