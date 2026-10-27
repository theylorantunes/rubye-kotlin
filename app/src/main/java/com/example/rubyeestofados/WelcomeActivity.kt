package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Verifica se o usuário já está logado
        val session = SessionManager(this)
        if (session.isLoggedIn()) {
            val intent = Intent(this, ProdutosActivity::class.java)
            startActivity(intent)
            finish() // Impede de voltar pra tela de welcome
            return
        }
        
        setContentView(R.layout.activity_welcome)

        val btnFazerLogin = findViewById<Button>(R.id.btnFazerLogin)
        val btnCriarConta = findViewById<Button>(R.id.btnCriarConta)

        // Vai para a tela de Login (que atualmente é a MainActivity)
        btnFazerLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Vai direto para a tela de Cadastro
        btnCriarConta.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}