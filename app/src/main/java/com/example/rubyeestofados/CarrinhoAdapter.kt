package com.example.rubyeestofados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarrinhoAdapter(
    private val itens: MutableList<ItemCarrinho>,
    private val onQuantidadeChanged: () -> Unit,
    private val onItemRemoved: (ItemCarrinho) -> Unit
) : RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder>() {

    class CarrinhoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivFoto: ImageView = view.findViewById(R.id.ivFotoCarrinho)
        val tvNome: TextView = view.findViewById(R.id.tvNomeCarrinho)
        val tvPreco: TextView = view.findViewById(R.id.tvPrecoCarrinho)
        val tvQuantidade: TextView = view.findViewById(R.id.tvQuantidade)
        val btnMenos: TextView = view.findViewById(R.id.btnMenos)
        val btnMais: TextView = view.findViewById(R.id.btnMais)
        val tvExcluir: TextView = view.findViewById(R.id.tvExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrinhoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrinho, parent, false)
        return CarrinhoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarrinhoViewHolder, position: Int) {
        val item = itens[position]
        
        holder.ivFoto.setImageResource(item.produto.imagemResId)
        holder.tvNome.text = item.produto.nome
        holder.tvPreco.text = item.produto.preco
        holder.tvQuantidade.text = item.quantidade.toString()

        holder.btnMais.setOnClickListener {
            item.quantidade++
            holder.tvQuantidade.text = item.quantidade.toString()
            onQuantidadeChanged()
        }

        holder.btnMenos.setOnClickListener {
            if (item.quantidade > 1) {
                item.quantidade--
                holder.tvQuantidade.text = item.quantidade.toString()
                onQuantidadeChanged()
            }
        }

        holder.tvExcluir.setOnClickListener {
            val currentPos = holder.bindingAdapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                onItemRemoved(item)
                notifyItemRemoved(currentPos)
            }
        }
    }

    override fun getItemCount(): Int = itens.size
}