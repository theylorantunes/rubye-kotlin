package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

// Objeto de dados para controlar os itens do carrinho (Produto + Quantidade)
data class ItemCarrinho(val produto: Produto, var quantidade: Int)

class CarrinhoActivity : AppCompatActivity() {

    private lateinit var adapter: CarrinhoAdapter
    private lateinit var tvTotal: TextView

    // Aqui simulamos a lista de itens com seus respectivos produtos e quantidades
    private val itensCarrinho = mutableListOf(
        ItemCarrinho(Produto("Sofá Retro Mission", "R$ 899,00", R.drawable.sofa_renan), 1),
        ItemCarrinho(Produto("Sofá Renascentist", "R$ 1.150,00", R.drawable.sofa_amem), 1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        BottomNavHelper.setupBottomNav(this, R.id.nav_carrinho)

        val rvCarrinho = findViewById<RecyclerView>(R.id.rvCarrinho)
        tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizar)

        // Configuração do RecyclerView e do Adapter
        adapter = CarrinhoAdapter(
            itensCarrinho,
            onQuantidadeChanged = {
                atualizarTotal() // Atualiza quando clicar em + ou -
            },
            onItemRemoved = { item ->
                itensCarrinho.remove(item)
                atualizarTotal() // Atualiza após remover
                Toast.makeText(this, "${item.produto.nome} removido.", Toast.LENGTH_SHORT).show()
            }
        )

        rvCarrinho.layoutManager = LinearLayoutManager(this)
        rvCarrinho.adapter = adapter

        // Calcula o primeiro total assim que a tela abre
        atualizarTotal()

        btnFinalizar.setOnClickListener {
            if (itensCarrinho.isNotEmpty()) {
                itensCarrinho.clear()
                adapter.notifyDataSetChanged()
                atualizarTotal()
                Toast.makeText(this, "Pedido finalizado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "O carrinho está vazio.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarTotal() {
        var total = 0.0
        for (item in itensCarrinho) {
            val precoDouble = parsePreco(item.produto.preco)
            total += precoDouble * item.quantidade
        }

        // Formata o número de volta para moeda brasileira (R$)
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        tvTotal.text = format.format(total)
    }

    // Pega o "R$ 899,00" (texto) e converte para "899.00" (número) para cálculo
    private fun parsePreco(precoStr: String): Double {
        return try {
            val cleanString = precoStr.replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim()
            cleanString.toDouble()
        } catch (e: Exception) {
            0.0
        }
    }
}