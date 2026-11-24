package com.example.rubyeestofados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdminProdutoAdapter(private val produtos: List<Produto>) : RecyclerView.Adapter<AdminProdutoAdapter.AdminProdutoViewHolder>() {

    class AdminProdutoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foto: ImageView = view.findViewById(R.id.ivAdminProdFoto)
        val nome: TextView = view.findViewById(R.id.tvAdminProdNome)
        val preco: TextView = view.findViewById(R.id.tvAdminProdPreco)
        val btnEdit: ImageView = view.findViewById(R.id.btnAdminEdit)
        val switchAtivo: SwitchCompat = view.findViewById(R.id.switchAtivo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminProdutoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_produto, parent, false)
        return AdminProdutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.nome.text = produto.nome
        holder.preco.text = produto.preco

        if (!produto.imagemUrl.isNullOrEmpty() && produto.imagemUrl.startsWith("http")) {
            Glide.with(holder.itemView.context).load(produto.imagemUrl).placeholder(R.drawable.sofa_l).into(holder.foto)
        } else {
            holder.foto.setImageResource(produto.imagemResId)
        }
        
        // Define o estado do Switch (Ativo/Inativo)
        holder.switchAtivo.isChecked = produto.ativo

        holder.switchAtivo.setOnCheckedChangeListener { _, isChecked ->
            produto.ativo = isChecked
            val statusTexto = if (isChecked) "Ativado" else "Inativado (Oculto da loja)"
            Toast.makeText(holder.itemView.context, "${produto.nome} $statusTexto", Toast.LENGTH_SHORT).show()
        }

        holder.btnEdit.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Abrir formulário de Edição para ${produto.nome}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = produtos.size
}
