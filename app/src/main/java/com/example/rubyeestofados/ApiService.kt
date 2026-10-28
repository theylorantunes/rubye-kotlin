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
}