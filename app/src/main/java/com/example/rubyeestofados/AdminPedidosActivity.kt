package com.example.rubyeestofados

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminPedidosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pedidos)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarAdminPed)
        val rvPedidos = findViewById<RecyclerView>(R.id.rvAdminPedidos)
        
        btnVoltar.setOnClickListener { finish() }

        // Mock para apresentar pro professor amanhã! 
        // O status de cada um começa diferente pra você mostrar a máquina de estados funcionando
        val mockPedidosAdmin = mutableListOf(
            MeuPedido("1001", "28/10/2026", "Pagamento Aprovado", "João Silva\nAv Paulista, 1000", 2800.0, emptyList()),
            MeuPedido("1002", "27/10/2026", "Em Produção", "Maria Oliveira\nRua Augusta, 500", 1150.0, emptyList()),
            MeuPedido("1003", "25/10/2026", "Entregue", "Carlos Souza\nRua das Flores, 12", 899.0, emptyList()),
            MeuPedido("1004", "29/10/2026", "Pronto para Envio", "Ana Paula\nAv Brasil, 22", 4500.0, emptyList())
        )

        val adapter = AdminPedidosAdapter(mockPedidosAdmin) { pedidoAtualizado ->
            // Aqui seria a chamada Retrofit (PUT ou POST) pra atualizar no PHP: admin_atualizar_pedido.php
            // Como é para apresentação, ele atualiza na tela e o RecyclerView já reflete a cor na hora!
        }

        rvPedidos.layoutManager = LinearLayoutManager(this)
        rvPedidos.adapter = adapter
        
        // --- Filtros ---
        // Aqui a gente pode usar os botões "btnFiltroPendentes" etc. que criamos no XML
        // Mas por enquanto exibe todos:
    }
}
