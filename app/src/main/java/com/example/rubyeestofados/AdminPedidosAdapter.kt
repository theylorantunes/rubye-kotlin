package com.example.rubyeestofados

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class AdminPedidosAdapter(
    private val pedidos: MutableList<MeuPedido>,
    private val onStatusChanged: (MeuPedido) -> Unit
) : RecyclerView.Adapter<AdminPedidosAdapter.AdminPedidoViewHolder>() {

    class AdminPedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNum = view.findViewById<TextView>(R.id.tvAdminPedNum)
        val tvStatus = view.findViewById<TextView>(R.id.tvAdminPedStatus)
        val tvCliente = view.findViewById<TextView>(R.id.tvAdminPedCliente)
        val tvData = view.findViewById<TextView>(R.id.tvAdminPedData)
        val tvTotal = view.findViewById<TextView>(R.id.tvAdminPedTotal)
        val btnMudarStatus = view.findViewById<Button>(R.id.btnAdminMudarStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminPedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_pedido, parent, false)
        return AdminPedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminPedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

        holder.tvNum.text = "Pedido #${pedido.id}"
        holder.tvData.text = "Data: ${pedido.data}"
        holder.tvCliente.text = "Cliente: ${pedido.endereco.split("\n").firstOrNull() ?: "Desconhecido"}" // mock do cliente pelo endereco
        holder.tvTotal.text = "Total: ${format.format(pedido.total)}"
        val statusAtual = pedido.status.uppercase()
        holder.tvStatus.text = statusAtual

        // Configuração de cores detalhadas
        when (statusAtual) {
            "PAGAMENTO APROVADO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#1565C0")) // Azul Escuro
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E3F2FD"))
            }
            "EM PRODUÇÃO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#E65100")) // Laranja Forte
                holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"))
            }
            "PRONTO PARA ENVIO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#00695C")) // Verde Teal
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E0F2F1"))
            }
            "ENVIADO" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#0277BD")) // Azul Claro
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E1F5FE"))
            }
            "ENTREGUE" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32")) // Verde Sucesso
                holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
            }
            else -> {
                holder.tvStatus.setTextColor(Color.parseColor("#757575"))
                holder.tvStatus.setBackgroundColor(Color.parseColor("#F5F5F5"))
            }
        }

        // Lógica da Máquina de Estados (Fluxo Direcional e Único)
        val fluxo = listOf("PAGAMENTO APROVADO", "EM PRODUÇÃO", "PRONTO PARA ENVIO", "ENVIADO", "ENTREGUE")
        val currentIndex = fluxo.indexOf(statusAtual)

        if (currentIndex != -1 && currentIndex < fluxo.size - 1) {
            holder.btnMudarStatus.visibility = View.VISIBLE
            
            // O botão já sabe qual é a próxima ação lógica
            when (statusAtual) {
                "PAGAMENTO APROVADO" -> holder.btnMudarStatus.text = "INICIAR PRODUÇÃO"
                "EM PRODUÇÃO" -> holder.btnMudarStatus.text = "MARCAR PRONTO"
                "PRONTO PARA ENVIO" -> holder.btnMudarStatus.text = "DESPACHAR"
                "ENVIADO" -> holder.btnMudarStatus.text = "CONFIRMAR ENTREGA"
            }

            holder.btnMudarStatus.setOnClickListener {
                val novoStatus = fluxo[currentIndex + 1]
                val context = holder.itemView.context

                // Transparência e Segurança: Pop-up de Confirmação antes de mudar o Status!
                AlertDialog.Builder(context)
                    .setTitle("Atualizar Pedido #${pedido.id}")
                    .setMessage("Confirma a alteração do status de:\n\n[${statusAtual}]\nPara:\n[${novoStatus}] ?\n\nIsso notificará o cliente.")
                    .setPositiveButton("CONFIRMAR") { _, _ ->
                        // Modifica o estado visualmente pra apresentação
                        val pedidoModificado = pedido.copy(status = novoStatus)
                        pedidos[position] = pedidoModificado
                        notifyItemChanged(position)
                        
                        onStatusChanged(pedidoModificado)
                        Toast.makeText(context, "Pedido atualizado! O cliente foi notificado.", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("VOLTAR", null)
                    .show()
            }
        } else {
            // Se já foi Entregue, o botão de avançar desaparece
            holder.btnMudarStatus.visibility = View.GONE
        }
    }

    override fun getItemCount() = pedidos.size
    
    fun updateData(novosPedidos: List<MeuPedido>) {
        pedidos.clear()
        pedidos.addAll(novosPedidos)
        notifyDataSetChanged()
    }
}
