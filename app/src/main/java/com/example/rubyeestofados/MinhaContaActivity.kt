package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MinhaContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_conta)

        // Configura a barra de navegação no fundo (Bottom Nav)
        BottomNavHelper.setupBottomNav(this, R.id.nav_conta)

        val btnSair = findViewById<Button>(R.id.btnSair)
        val btnMeusPedidos = findViewById<Button>(R.id.btnMeusPedidos)
        val btnEditarPerfil = findViewById<Button>(R.id.btnEditarPerfil)

        btnMeusPedidos.setOnClickListener {
            Toast.makeText(this, "Em breve: Seus pedidos!", Toast.LENGTH_SHORT).show()
        }

        btnEditarPerfil.setOnClickListener {
            Toast.makeText(this, "Em breve: Edição de perfil!", Toast.LENGTH_SHORT).show()
        }

        btnSair.setOnClickListener {
            // Limpa a sessão de login salva
            val session = SessionManager(this)
            session.clearSession()
            
            // Volta para a tela inicial limpando a pilha de telas
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}