package com.example.rubyeestofados

import android.app.AlertDialog
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

class AdminLeiloesAdapter(private val leiloes: MutableList<ItemLeilao>) : RecyclerView.Adapter<AdminLeiloesAdapter.AdminLeilaoViewHolder>() {

    class AdminLeilaoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foto: ImageView = view.findViewById(R.id.ivAdminLeilaoFoto)
        val nome: TextView = view.findViewById(R.id.tvAdminLeilaoNome)
        val vencedor: TextView = view.findViewById(R.id.tvAdminLeilaoVencedor)
        val valor: TextView = view.findViewById(R.id.tvAdminLeilaoValor)
        val tempo: TextView = view.findViewById(R.id.tvAdminLeilaoTempo)
        val btnEncerrar: Button = view.findViewById(R.id.btnAdminLeilaoEncerrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminLeilaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_leilao, parent, false)
        return AdminLeilaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminLeilaoViewHolder, position: Int) {
        val item = leiloes[position]
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

        holder.nome.text = item.nome
        holder.valor.text = format.format(item.lanceAtual)
        holder.tempo.text = item.tempoRestante
        holder.foto.setImageResource(item.imagemResId)
        
        // Mock do top bidder
        val topBidders = listOf("Fallen_BR", "Coldzera22", "Gaules_Tribe")
        holder.vencedor.text = "Top Lance: ${topBidders[position % 3]}"

        holder.btnEncerrar.setOnClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setTitle("Forçar Encerramento")
                .setMessage("Deseja encerrar este leilão antecipadamente? O vencedor atual será notificado para realizar o pagamento.")
                .setPositiveButton("ENCERRAR") { _, _ ->
                    // Zera o cronometro e avisa o lojista
                    item.tempoRestante = "00:00:00"
                    holder.tempo.text = item.tempoRestante
                    holder.btnEncerrar.isEnabled = false
                    holder.btnEncerrar.text = "ENCERRADO"
                    
                    Toast.makeText(context, "Leilão encerrado. Fatura gerada para o vencedor.", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("CANCELAR", null)
                .show()
        }
    }

    override fun getItemCount() = leiloes.size
}
