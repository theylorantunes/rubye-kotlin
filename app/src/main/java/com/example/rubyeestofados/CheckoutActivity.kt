package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etNome = findViewById<EditText>(R.id.etNome)
        val etCep = findViewById<EditText>(R.id.etCep)
        val etEndereco = findViewById<EditText>(R.id.etEndereco)
        
        val rgPagamento = findViewById<RadioGroup>(R.id.rgPagamento)
        val llPixArea = findViewById<LinearLayout>(R.id.llPixArea)
        val btnConfirmarPagamentoPix = findViewById<Button>(R.id.btnConfirmarPagamentoPix)

        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val llDescontoArea = findViewById<LinearLayout>(R.id.llDescontoArea)
        val tvDesconto = findViewById<TextView>(R.id.tvDesconto)
        val tvTotalFinal = findViewById<TextView>(R.id.tvTotalFinal)

        // preencher dados do usuario (se estiver logado)
        val session = SessionManager(this)
        etEmail.setText(session.getUserEmail())
        etNome.setText(session.getUserNome())

        // calculos financeiros
        val totalSemDesconto = CartManager.getTotal()
        val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))
        
        tvSubtotal.text = format.format(totalSemDesconto)
        tvTotalFinal.text = format.format(totalSemDesconto)

        var totalComDescontoAtual = totalSemDesconto

        // Integração com ViaCEP
        etCep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val cep = s.toString().replace("-", "").trim()
                if (cep.length == 8) {
                    Toast.makeText(this@CheckoutActivity, "Buscando CEP...", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        try {
                            val response = ViaCepClient.apiService.consultarCep(cep)
                            if (response.isSuccessful && response.body()?.erro != true) {
                                val cepData = response.body()!!
                                val enderecoCompleto = "${cepData.logradouro}, ${cepData.bairro} - ${cepData.localidade}/${cepData.uf}"
                                etEndereco.setText(enderecoCompleto)
                            } else {
                                Toast.makeText(this@CheckoutActivity, "CEP não encontrado.", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@CheckoutActivity, "Erro ao buscar CEP.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        // logica do radiogroup (pix)
        rgPagamento.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbPix) {
                // mostra area do pix e calcula desconto de 5%
                llPixArea.visibility = View.VISIBLE
                llDescontoArea.visibility = View.VISIBLE
                
                val desconto = totalSemDesconto * 0.05
                totalComDescontoAtual = totalSemDesconto - desconto
                
                tvDesconto.text = "- ${format.format(desconto)}"
                tvTotalFinal.text = format.format(totalComDescontoAtual)
                
            } else {
                // esconde e reseta valor
                llPixArea.visibility = View.GONE
                llDescontoArea.visibility = View.GONE
                totalComDescontoAtual = totalSemDesconto
                tvTotalFinal.text = format.format(totalSemDesconto)
            }
        }

        btnConfirmarPagamentoPix.setOnClickListener {
            val nome = etNome.text.toString()
            val email = etEmail.text.toString()
            val cep = etCep.text.toString()
            val endereco = etEndereco.text.toString()

            if (nome.isBlank() || cep.isBlank() || endereco.isBlank()) {
                Toast.makeText(this, "Por favor, preencha as informações de entrega.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // desativa botao pra evitar duplo clique
            btnConfirmarPagamentoPix.isEnabled = false
            btnConfirmarPagamentoPix.text = "PROCESSANDO..."

            val itensRequest = CartManager.getItens().map {
                val precoStr = it.produto.preco.replace("R$", "").replace(" ", "").replace(".", "").replace(",", ".").trim()
                ItemPedidoRequest(
                    produtoNome = it.produto.nome,
                    quantidade = it.quantidade,
                    precoUnitario = precoStr.toDoubleOrNull() ?: 0.0
                )
            }

            val pedido = PedidoRequest(
                usuarioEmail = email,
                nome = nome,
                cep = cep,
                endereco = endereco,
                total = totalComDescontoAtual,
                formaPagamento = "PIX",
                itens = itensRequest
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.enviarPedido(pedido)
                    if (response.isSuccessful && response.body()?.sucesso == true) {
                        CartManager.limparCarrinho()
                        val intent = Intent(this@CheckoutActivity, PedidoSucessoActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@CheckoutActivity, "Erro ao registrar pedido: ${response.body()?.mensagem}", Toast.LENGTH_LONG).show()
                        btnConfirmarPagamentoPix.isEnabled = true
                        btnConfirmarPagamentoPix.text = "JÁ FIZ O PAGAMENTO"
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@CheckoutActivity, "Falha de conexão com a API", Toast.LENGTH_SHORT).show()
                    btnConfirmarPagamentoPix.isEnabled = true
                    btnConfirmarPagamentoPix.text = "JÁ FIZ O PAGAMENTO"
                }
            }
        }
    }
}
