package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

        // logica do radiogroup (pix)
        rgPagamento.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbPix) {
                // mostra area do pix e calcula desconto de 5%
                llPixArea.visibility = View.VISIBLE
                llDescontoArea.visibility = View.VISIBLE
                
                val desconto = totalSemDesconto * 0.05
                val totalComDesconto = totalSemDesconto - desconto
                
                tvDesconto.text = "- ${format.format(desconto)}"
                tvTotalFinal.text = format.format(totalComDesconto)
                
            } else {
                // esconde e reseta valor (nao deve cair aqui pois so deixei pix no layout novo, mas fica seguro)
                llPixArea.visibility = View.GONE
                llDescontoArea.visibility = View.GONE
                tvTotalFinal.text = format.format(totalSemDesconto)
            }
        }

        btnConfirmarPagamentoPix.setOnClickListener {
            if (etNome.text.isBlank() || etCep.text.isBlank() || etEndereco.text.isBlank()) {
                Toast.makeText(this, "Por favor, preencha as informações de entrega.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // finaliza compra
            CartManager.limparCarrinho()
            val intent = Intent(this, PedidoSucessoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
