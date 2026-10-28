package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ProdutosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        BottomNavHelper.setupBottomNav(this, R.id.nav_produtos)

        val rvProdutos = findViewById<RecyclerView>(R.id.rvProdutos)
        rvProdutos.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch {
            try {
                // 1. Chamada à API
                val response = RetrofitClient.apiService.listarProdutos()
                
                // 2. JSON vira lista de objetos (ProdutoApi)
                val listaApi: List<ProdutoApi> = response.body().orEmpty()
                
                // Conversão de ProdutoApi para o Produto esperado pelo Adapter
                val listaProdutos = listaApi.map { produtoApi ->
                    Produto(
                        nome = produtoApi.nome,
                        preco = "R$ ${produtoApi.preco}",
                        imagemResId = R.drawable.sofa_l // Imagem padrão enquanto a API não manda URL
                    )
                }

                // 4. RecyclerView recebe o adapter
                val adapter = ProdutoAdapter(listaProdutos)
                rvProdutos.adapter = adapter
                
            } catch (e: Exception) {
                Log.e("ProdutosActivity", "Erro ao chamar produtos.php", e)
            }
        }
    }
}