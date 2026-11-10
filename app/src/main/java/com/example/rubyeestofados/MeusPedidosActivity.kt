package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MeusPedidosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_pedidos)

        val rvMeusPedidos = findViewById<RecyclerView>(R.id.rvMeusPedidos)
        rvMeusPedidos.layoutManager = LinearLayoutManager(this)

        val session = SessionManager(this)
        val userEmail = session.getUserEmail()

        lifecycleScope.launch {
            try {
                // 1. Busca os pedidos do cliente logado
                val response = RetrofitClient.apiService.listarMeusPedidos(userEmail)
                
                if (response.isSuccessful && response.body() != null) {
                    val pedidosApi = response.body()!!
                    val meusPedidosList = mutableListOf<MeuPedido>()
                    val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

                    // 2. Para cada pedido, busca os itens
                    for (pedApi in pedidosApi) {
                        val responseItens = RetrofitClient.apiService.listarItensPedido(pedApi.id)
                        val itensConvertidos = mutableListOf<ItemPedido>()

                        if (responseItens.isSuccessful && responseItens.body() != null) {
                            for (itemApi in responseItens.body()!!) {
                                itensConvertidos.add(
                                    ItemPedido(
                                        nome = itemApi.produto_nome,
                                        quantidade = itemApi.quantidade,
                                        preco = format.format(itemApi.preco_unitario)
                                    )
                                )
                            }
                        }

                        // Formata a data (de 2026-10-28 15:30:00 para 28/10/2026)
                        val dataFormatada = try {
                            val partes = pedApi.data_pedido.split(" ")[0].split("-")
                            "${partes[2]}/${partes[1]}/${partes[0]}"
                        } catch (e: Exception) { pedApi.data_pedido }

                        meusPedidosList.add(
                            MeuPedido(
                                id = pedApi.id,
                                data = dataFormatada,
                                status = pedApi.status_pedido,
                                endereco = pedApi.endereco,
                                total = pedApi.total,
                                itens = itensConvertidos
                            )
                        )
                    }

                    rvMeusPedidos.adapter = MeusPedidosAdapter(meusPedidosList)
                } else {
                    Toast.makeText(this@MeusPedidosActivity, "Nenhum pedido encontrado.", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@MeusPedidosActivity, "Erro ao carregar os pedidos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
