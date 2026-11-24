package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class AdminProdutosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_produtos)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarAdminProd)
        val btnAdicionar = findViewById<ExtendedFloatingActionButton>(R.id.btnAdicionarProduto)
        val rvAdminProdutos = findViewById<RecyclerView>(R.id.rvAdminProdutos)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnAdicionar.setOnClickListener {
            val intent = Intent(this, AdminNovoProdutoActivity::class.java)
            startActivity(intent)
        }

        // Mock pra ver a tela de admin produtos rodando
        val mockProdutos = listOf(
            Produto("Sofá Retro Mission", "R$ 899,00", R.drawable.sofa_renan),
            Produto("Sofá Renascentist", "R$ 1.150,00", R.drawable.sofa_amem),
            Produto("Sofá Premium em L", "R$ 599,00", R.drawable.sofa_l)
        )

        rvAdminProdutos.layoutManager = LinearLayoutManager(this)
        // Usa o mesmo adapter modificado do catálogo normal pra gente economizar tempo? Nao, vou criar um rapido pra ter o Editar/Deletar
        rvAdminProdutos.adapter = AdminProdutoAdapter(mockProdutos)
    }
}
