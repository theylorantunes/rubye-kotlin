package com.example.rubyeestofados

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class DetalhesPedidoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pedido)

        val tvVoltar = findViewById<TextView>(R.id.tvVoltar)
        val tvNum = findViewById<TextView>(R.id.tvNumDetalhe)
        val tvData = findViewById<TextView>(R.id.tvDataDetalhe)
        val tvStatus = findViewById<TextView>(R.id.tvStatusDetalhe)
        val tvEndereco = findViewById<TextView>(R.id.tvEnderecoDetalhe)
        val tvTotal = findViewById<TextView>(R.id.tvTotalDetalhe)
        val llItensContainer = findViewById<LinearLayout>(R.id.llItensContainer)

        tvVoltar.setOnClickListener {
            finish()
        }

        // Pega o objeto MeuPedido enviado da tela anterior
        val pedido = intent.getSerializableExtra("PEDIDO") as? MeuPedido

        if (pedido != null) {
            val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

            tvNum.text = "Pedido #${pedido.id}"
            tvData.text = "Data: ${pedido.data}"
            tvStatus.text = "STATUS: ${pedido.status.uppercase()}"
            tvEndereco.text = pedido.endereco
            tvTotal.text = "Total: ${format.format(pedido.total)}"

            if (pedido.status.uppercase() == "ENTREGUE") {
                tvStatus.setTextColor(Color.parseColor("#388E3C"))
                tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
            } else {
                tvStatus.setTextColor(Color.parseColor("#F57C00"))
                tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"))
            }

            // Injeta os itens do pedido visualmente
            for (item in pedido.itens) {
                val tvItem = TextView(this).apply {
                    text = "${item.quantidade}x ${item.nome}\nValor Unitário: ${item.preco}"
                    textSize = 14f
                    setTextColor(Color.parseColor("#4A3B22"))
                    setPadding(0, 0, 0, 24) // Margem embaixo pra separar
                }
                llItensContainer.addView(tvItem)
            }
        }
    }
}
