package com.example.rubyeestofados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

data class ItemLeilao(
    val id: Int,
    val nome: String,
    var lanceAtual: Double,
    var tempoRestante: String,
    val imagemResId: Int
)

class LeilaoAdapter(private val listaLeiloes: List<ItemLeilao>) : RecyclerView.Adapter<LeilaoAdapter.LeilaoViewHolder>() {

    class LeilaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.ivFotoLeilao)
        val nome: TextView = itemView.findViewById(R.id.tvNomeLeilao)
        val lanceAtual: TextView = itemView.findViewById(R.id.tvLanceAtual)
        val tempoRestante: TextView = itemView.findViewById(R.id.tvTempoRestante)
        val btnDarLance: Button = itemView.findViewById(R.id.btnDarLance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeilaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leilao, parent, false)
        return LeilaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeilaoViewHolder, position: Int) {
        val item = listaLeiloes[position]
        
        holder.nome.text = item.nome
        holder.tempoRestante.text = item.tempoRestante
        holder.foto.setImageResource(item.imagemResId)
        
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        holder.lanceAtual.text = format.format(item.lanceAtual)
        
        holder.btnDarLance.setOnClickListener {
            // Simulando um lance + 50 reais
            item.lanceAtual += 50.0
            holder.lanceAtual.text = format.format(item.lanceAtual)
            Toast.makeText(holder.itemView.context, "Lance coberto! Você está ganhando.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listaLeiloes.size
}
