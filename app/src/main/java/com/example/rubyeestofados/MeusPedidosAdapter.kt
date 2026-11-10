package com.example.rubyeestofados

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class MeusPedidosAdapter(private val pedidos: List<MeuPedido>) : RecyclerView.Adapter<MeusPedidosAdapter.PedidoViewHolder>() {

    class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNum = view.findViewById<TextView>(R.id.tvNumPedido)
        val tvStatus = view.findViewById<TextView>(R.id.tvStatusPedido)
        val tvData = view.findViewById<TextView>(R.id.tvDataPedido)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotalPedido)
        val btnDetalhes = view.findViewById<Button>(R.id.btnVerDetalhes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meu_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

        holder.tvNum.text = "Pedido #${pedido.id}"
        holder.tvData.text = "Realizado em ${pedido.data}"
        holder.tvTotal.text = "Total: ${format.format(pedido.total)}"
        holder.tvStatus.text = pedido.status

        if (pedido.status.uppercase() == "ENTREGUE") {
            holder.tvStatus.setTextColor(Color.parseColor("#388E3C"))
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
        } else {
            // "PROCESSANDO" ou "A CAMINHO"
            holder.tvStatus.setTextColor(Color.parseColor("#F57C00"))
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"))
        }

        holder.btnDetalhes.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalhesPedidoActivity::class.java).apply {
                putExtra("PEDIDO", pedido)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = pedidos.size
}
