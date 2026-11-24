package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AdminNovoProdutoActivity : AppCompatActivity() {

    private val linksImagens = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_novo_produto)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarNovoProd)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarProduto)
        
        val llAddFoto = findViewById<LinearLayout>(R.id.llAddFoto)
        val llListaFotos = findViewById<LinearLayout>(R.id.llListaFotos)

        btnVoltar.setOnClickListener {
            finish()
        }
        
        llAddFoto.setOnClickListener {
            if (linksImagens.size >= 5) {
                Toast.makeText(this, "Máximo de 5 imagens atingido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Cria um campo de texto dinâmico na tela para o lojista colar o link
            val etLink = EditText(this).apply {
                hint = "Cole aqui o link HTTP da imagem"
                textSize = 14f
                setPadding(16, 24, 16, 24)
                setBackgroundResource(R.drawable.bg_input_checkout)
            }
            
            // Cria um layout em linha pro EditText com uma margem de respiro em baixo
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
            
            llListaFotos.addView(etLink, layoutParams)
            linksImagens.add("") // reserva espaço na memoria
            
            etLink.requestFocus()
        }

        val etNome = findViewById<EditText>(R.id.etNovoProdNome)
        val etPreco = findViewById<EditText>(R.id.etNovoProdPreco)
        val etDescricao = findViewById<EditText>(R.id.etNovoProdDescricao)
        val etTags = findViewById<EditText>(R.id.etNovoProdTags)
        val etDestaque = findViewById<EditText>(R.id.etNovoProdDestaque)

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString()
            val precoStr = etPreco.text.toString()
            val descricao = etDescricao.text.toString()
            val tags = etTags.text.toString()
            val destaque = etDestaque.text.toString()

            if (nome.isBlank() || precoStr.isBlank()) {
                Toast.makeText(this, "Nome e Preço são obrigatórios!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precoDouble = precoStr.replace(",", ".").toDoubleOrNull() ?: 0.0

            val imagensUrls = mutableListOf<String>()
            for (i in 0 until llListaFotos.childCount) {
                val et = llListaFotos.getChildAt(i) as EditText
                if (et.text.isNotBlank()) {
                    imagensUrls.add(et.text.toString())
                }
            }

            btnSalvar.isEnabled = false
            btnSalvar.text = "SALVANDO..."

            val request = ProdutoRequest(
                nome = nome,
                preco = precoDouble,
                descricao = descricao,
                tags = tags,
                destaque = destaque,
                imagens = imagensUrls
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.criarProduto(request)
                    if (response.isSuccessful && response.body()?.sucesso == true) {
                        Toast.makeText(this@AdminNovoProdutoActivity, "Produto salvo no Catálogo!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AdminNovoProdutoActivity, "Erro: ${response.body()?.mensagem}", Toast.LENGTH_LONG).show()
                        btnSalvar.isEnabled = true
                        btnSalvar.text = "SALVAR NO CATÁLOGO"
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@AdminNovoProdutoActivity, "Erro de rede ao salvar", Toast.LENGTH_SHORT).show()
                    btnSalvar.isEnabled = true
                    btnSalvar.text = "SALVAR NO CATÁLOGO"
                }
            }
        }
    }
}
