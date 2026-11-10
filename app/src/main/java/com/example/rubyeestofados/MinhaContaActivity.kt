package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MinhaContaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_conta)

        // configura a barra de navegacao no fundo (bottom nav)
        BottomNavHelper.setupBottomNav(this, R.id.nav_conta)

        val btnSair = findViewById<Button>(R.id.btnSair)
        val btnMeusPedidos = findViewById<Button>(R.id.btnMeusPedidos)
        val btnEditarPerfil = findViewById<Button>(R.id.btnEditarPerfil)
        val tvNomeUsuario = findViewById<TextView>(R.id.tvNomeUsuario)
        val tvEmailUsuario = findViewById<TextView>(R.id.tvEmailUsuario)

        // resgata os dados da sessao
        val session = SessionManager(this)
        tvNomeUsuario.text = session.getUserNome()
        tvEmailUsuario.text = session.getUserEmail()

        btnMeusPedidos.setOnClickListener {
            val intent = Intent(this, MeusPedidosActivity::class.java)
            startActivity(intent)
        }

        btnEditarPerfil.setOnClickListener {
            Toast.makeText(this, "Em breve: Edição de perfil!", Toast.LENGTH_SHORT).show()
        }

        btnSair.setOnClickListener {
            // limpa a sessao de login salva
            val session = SessionManager(this)
            session.clearSession()
            
            // volta para a tela inicial limpando a pilha de telas
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}