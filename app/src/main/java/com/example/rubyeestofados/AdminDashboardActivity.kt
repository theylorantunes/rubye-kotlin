package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarAdmin)
        val btnAdminProdutos = findViewById<Button>(R.id.btnAdminProdutos)
        val btnAdminPedidos = findViewById<Button>(R.id.btnAdminPedidos)
        val btnAdminLeiloes = findViewById<Button>(R.id.btnAdminLeiloes)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnAdminProdutos.setOnClickListener {
            val intent = Intent(this, AdminProdutosActivity::class.java)
            startActivity(intent)
        }

        btnAdminPedidos.setOnClickListener {
            val intent = Intent(this, AdminPedidosActivity::class.java)
            startActivity(intent)
        }

        btnAdminLeiloes.setOnClickListener {
            val intent = Intent(this, AdminLeiloesActivity::class.java)
            startActivity(intent)
        }
    }
}
