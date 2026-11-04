package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PedidoSucessoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_sucesso)

        val btnVoltarCatalogo = findViewById<Button>(R.id.btnVoltarCatalogo)

        btnVoltarCatalogo.setOnClickListener {
            irParaCatalogo()
        }

        // delay brabo de 7 segundos pra redirecionar sozinho (como voce pediu!)
        Handler(Looper.getMainLooper()).postDelayed({
            irParaCatalogo()
        }, 7000)
    }

    private fun irParaCatalogo() {
        val intent = Intent(this, ProdutosActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
