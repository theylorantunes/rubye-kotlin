package com.example.rubyeestofados

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProdutosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        val rvProdutos = findViewById<RecyclerView>(R.id.rvProdutos)


        val meusProdutos = listOf(
            Produto("Sofá Retro Mission", "R$ 899,00", R.drawable.sofa_renan),
            Produto("Sofá Renascentist", "R$ 1.150,00", R.drawable.sofa_amem),
            Produto("Sofá Premium em L", "R$ 599,00", R.drawable.sofa_l),
        )


        rvProdutos.layoutManager = GridLayoutManager(this, 2)
        rvProdutos.adapter = ProdutoAdapter(meusProdutos)
    }
}