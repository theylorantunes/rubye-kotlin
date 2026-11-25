package com.example.rubyeestofados.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rubyeestofados.PedidoSucessoActivity
import com.example.rubyeestofados.R

class PagamentoActivity : AppCompatActivity() {

    private lateinit var webViewAbacate: WebView
    private lateinit var llLoading: LinearLayout
    private lateinit var tvStatusPagamento: TextView

    private var pedidoId: String? = null
    private var urlCheckout: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagamento)

        webViewAbacate = findViewById(R.id.webViewAbacate)
        llLoading = findViewById(R.id.llLoading)
        tvStatusPagamento = findViewById(R.id.tvStatusPagamento)

        urlCheckout = intent.getStringExtra("copiaCola")
        pedidoId = intent.getStringExtra("idPedido")

        if (urlCheckout != null && pedidoId != null) {
            setupWebView()
            webViewAbacate.loadUrl(urlCheckout!!)
        } else {
            Toast.makeText(this, "Erro ao carregar link de pagamento", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webViewAbacate.settings.javaScriptEnabled = true
        webViewAbacate.settings.domStorageEnabled = true
        
        webViewAbacate.webViewClient = object : WebViewClient() {
            
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Quando o Abacate terminar de carregar, esconde o loading e mostra o navegador
                llLoading.visibility = View.GONE
                webViewAbacate.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val urlCarregada = request?.url?.toString() ?: ""

                // Espião: Se a URL tiver "thyagoquintas" ou "sucesso", o app intercepta na hora!
                if (urlCarregada.contains("thyagoquintas.com.br") || urlCarregada.contains("sucesso")) {
                    Toast.makeText(this@PagamentoActivity, "Pagamento Concluído!", Toast.LENGTH_SHORT).show()
                    
                    val successIntent = Intent(this@PagamentoActivity, PedidoSucessoActivity::class.java)
                    startActivity(successIntent)
                    finish()

                    return true // Bloqueia o navegador de ir pra página de erro
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}
