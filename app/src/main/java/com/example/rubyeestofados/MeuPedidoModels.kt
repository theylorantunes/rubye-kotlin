package com.example.rubyeestofados

import java.io.Serializable

data class ItemPedido(
    val nome: String,
    val quantidade: Int,
    val preco: String
) : Serializable

data class MeuPedido(
    val id: String,
    val data: String,
    val status: String,
    val endereco: String,
    val total: Double,
    val itens: List<ItemPedido>
) : Serializable

// Respostas da API
data class PedidosResponse(
    val id: String,
    val data_pedido: String,
    val status_pedido: String,
    val endereco: String,
    val total: Double
)

data class ItensPedidoResponse(
    val produto_nome: String,
    val quantidade: Int,
    val preco_unitario: Double
)
