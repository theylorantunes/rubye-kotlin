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
        val statusAtual = pedido.status.uppercase()
        holder.tvStatus.text = statusAtual

        // Configuração de cores detalhadas (Espelho do Admin)
        when (statusAtual) {
            "PAGAMENTO APROVADO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#1565C0")) 
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E3F2FD"))
            }
            "EM PRODUÇÃO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#E65100")) 
                holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"))
            }
            "PRONTO PARA ENVIO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#00695C")) 
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E0F2F1"))
            }
            "ENVIADO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#0277BD")) 
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E1F5FE"))
            }
            "ENTREGUE" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32")) 
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
            }
            else -> {
                holder.tvStatus.setTextColor(Color.parseColor("#757575"))
                holder.tvStatus.setBackgroundColor(Color.parseColor("#F5F5F5"))
            }
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
