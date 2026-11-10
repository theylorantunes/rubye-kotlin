package com.example.rubyeestofados

data class PedidoRequest(
    val usuarioEmail: String,
    val nome: String,
    val cep: String,
    val endereco: String,
    val total: Double,
    val formaPagamento: String,
    val itens: List<ItemPedidoRequest>
)

data class ItemPedidoRequest(
    val produtoNome: String,
    val quantidade: Int,
    val precoUnitario: Double
)

data class DefaultResponse(
    val sucesso: Boolean,
    val mensagem: String
)