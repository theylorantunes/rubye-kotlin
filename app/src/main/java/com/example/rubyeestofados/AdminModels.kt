package com.example.rubyeestofados

data class ProdutoRequest(
    val nome: String,
    val preco: Double,
    val descricao: String,
    val tags: String,
    val destaque: String,
    val imagens: List<String>
)

data class ProdutoResponse(
    val sucesso: Boolean,
    val mensagem: String
)
