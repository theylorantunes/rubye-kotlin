package com.example.rubyeestofados.payment

data class IntencaoPagamentoResponse(
    val sucesso: Boolean,
    val mensagem: String?,
    val qrCodeBase64: String?,
    val copiaCola: String?,
    val idPedido: String?
)

data class StatusPagamentoResponse(
    val status: String // "pendente", "aprovado", "recusado", "expirado"
)
