package com.example.rubyeestofados

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class ViaCepResponse(
    val logradouro: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val erro: Boolean?
)

interface ViaCepService {
    @GET("{cep}/json/")
    suspend fun consultarCep(@Path("cep") cep: String): Response<ViaCepResponse>
}

object ViaCepClient {
    val apiService: ViaCepService by lazy {
        Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepService::class.java)
    }
}
