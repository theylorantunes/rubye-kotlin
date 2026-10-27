package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalhesProdutoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_produto)

        val ivFoto = findViewById<ImageView>(R.id.ivFotoDetalhe)
        val tvNome = findViewById<TextView>(R.id.tvNomeDetalhe)
        val tvPreco = findViewById<TextView>(R.id.tvPrecoDetalhe)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnComprar = findViewById<Button>(R.id.btnComprar)
        val btnIrCarrinho = findViewById<ImageView>(R.id.btnIrCarrinho)

        val nome = intent.getStringExtra("NOME_PRODUTO") ?: "Produto sem nome"
        val preco = intent.getStringExtra("PRECO_PRODUTO") ?: "R$ 0,00"
        val imagemResId = intent.getIntExtra("IMAGEM_PRODUTO", R.drawable.logo_rubye)

        tvNome.text = nome
        tvPreco.text = preco
        ivFoto.setImageResource(imagemResId)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnIrCarrinho.setOnClickListener {
            val intent = android.content.Intent(this, CarrinhoActivity::class.java)
            startActivity(intent)
        }

        btnComprar.setOnClickListener {
            Toast.makeText(this, "Produto adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
        }
    }
}