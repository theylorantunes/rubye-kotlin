package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class CarrinhoActivity : AppCompatActivity() {

    private lateinit var adapter: CarrinhoAdapter
    private lateinit var tvTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        BottomNavHelper.setupBottomNav(this, R.id.nav_carrinho)

        val rvCarrinho = findViewById<RecyclerView>(R.id.rvCarrinho)
        tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizar)

        // usa os itens do cartmanager global
        adapter = CarrinhoAdapter(
            CartManager.getItens(),
            onQuantidadeChanged = {
                atualizarTotal() 
            },
            onItemRemoved = { item ->
                CartManager.removerProduto(item.produto)
                adapter.notifyDataSetChanged()
                atualizarTotal() 
                Toast.makeText(this, "${item.produto.nome} removido.", Toast.LENGTH_SHORT).show()
            }
        )

        rvCarrinho.layoutManager = LinearLayoutManager(this)
        rvCarrinho.adapter = adapter

        atualizarTotal()

        btnFinalizar.setOnClickListener {
            if (CartManager.getItens().isNotEmpty()) {
                // vai pra tela de checkout
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "O carrinho está vazio.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        atualizarTotal()
    }

    private fun atualizarTotal() {
        val total = CartManager.getTotal()
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        tvTotal.text = format.format(total)
    }
}