package com.example.rubyeestofados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

data class Produto(val nome: String, val preco: String, val imagemResId: Int)

class ProdutoAdapter(private val listaProdutos: List<Produto>) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.ivFotoProduto)
        val nome: TextView = itemView.findViewById(R.id.tvNomeProduto)
        val preco: TextView = itemView.findViewById(R.id.tvPrecoProduto)
        val btnAdicionar: View = itemView.findViewById(R.id.btnAdicionar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = listaProdutos[position]
        holder.nome.text = produto.nome
        holder.preco.text = produto.preco
        holder.foto.setImageResource(produto.imagemResId)

        // clique no card leva para a tela de detalhes
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = android.content.Intent(context, DetalhesProdutoActivity::class.java).apply {
                putExtra("NOME_PRODUTO", produto.nome)
                putExtra("PRECO_PRODUTO", produto.preco)
                putExtra("IMAGEM_PRODUTO", produto.imagemResId)
            }
            context.startActivity(intent)
        }

        // clique no botao '+' agora joga direto pro carrinho!
        holder.btnAdicionar.setOnClickListener {
            CartManager.adicionarProduto(produto)
            Toast.makeText(holder.itemView.context, "${produto.nome} adicionado!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listaProdutos.size
}