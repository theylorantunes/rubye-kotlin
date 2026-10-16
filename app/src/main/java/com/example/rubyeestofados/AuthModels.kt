package com.example.rubyeestofados

data class CadastroRequest(val nome: String, val email: String, val senha: String)

data class AuthResponse(val sucesso: Boolean, val mensagem: String?, val token: String?)

data class LoginResponse(
    val usuarioId: Int,
    val usuarioNome: String,
    val usuarioEmail: String,
    val usuarioSenha: String?,
    val usuarioCpf: String?
)