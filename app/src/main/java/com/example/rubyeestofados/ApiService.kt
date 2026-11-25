package com.example.rubyeestofados

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("login.php")
    suspend fun realizarLogin(
        @Query("usuario") email: String,
        @Query("senha") senha: String
    ): Response<List<LoginResponse>>


    @POST("cadastro.php")
    suspend fun realizarCadastro(@Body request: CadastroRequest): Response<AuthResponse>

    @GET("produtos.php")
    suspend fun listarProdutos(): Response<List<ProdutoApi>>

    @POST("pedidos.php")
    suspend fun enviarPedido(@Body pedido: PedidoRequest): Response<DefaultResponse>

    @GET("listar_meus_pedidos.php")
    suspend fun listarMeusPedidos(@Query("email") email: String): Response<List<PedidosResponse>>

    @GET("listar_itens_pedido.php")
    suspend fun listarItensPedido(@Query("pedido_id") pedidoId: String): Response<List<ItensPedidoResponse>>

    @POST("admin_criar_produto.php")
    suspend fun criarProduto(@Body produto: ProdutoRequest): Response<ProdutoResponse>

    @POST("criar_intencao_pagamento.php")
    suspend fun criarIntencaoPagamento(@Body pedido: PedidoRequest): Response<com.example.rubyeestofados.payment.IntencaoPagamentoResponse>

    @GET("verificar_status_pagamento.php")
    suspend fun verificarStatusPagamento(@Query("pedido_id") pedidoId: String): Response<com.example.rubyeestofados.payment.StatusPagamentoResponse>
}