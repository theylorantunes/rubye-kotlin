package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // verifica se o usuario ja esta logado
        val session = SessionManager(this)
        if (session.isLoggedIn()) {
            val intent = Intent(this, ProdutosActivity::class.java)
            startActivity(intent)
            finish() // impede de voltar pra tela de welcome
            return
        }
        
        setContentView(R.layout.activity_welcome)

        val btnFazerLogin = findViewById<Button>(R.id.btnFazerLogin)
        val btnCriarConta = findViewById<Button>(R.id.btnCriarConta)

        // vai para a tela de login (que atualmente e a mainactivity)
        btnFazerLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // vai direto para a tela de cadastro
        btnCriarConta.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}