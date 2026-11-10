package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ProdutosActivity : AppCompatActivity() {

    private var listaCompleta = listOf<Produto>()
    private lateinit var rvProdutos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        BottomNavHelper.setupBottomNav(this, R.id.nav_produtos)

        rvProdutos = findViewById(R.id.rvProdutos)
        rvProdutos.layoutManager = GridLayoutManager(this, 2)

        val etBusca = findViewById<EditText>(R.id.etBusca)
        val btnFiltroTodos = findViewById<Button>(R.id.btnFiltroTodos)
        val btnFiltroRetro = findViewById<Button>(R.id.btnFiltroRetro)
        val btnFiltroPremium = findViewById<Button>(R.id.btnFiltroPremium)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.listarProdutos()
                val listaApi: List<ProdutoApi> = response.body().orEmpty()
                
                listaCompleta = listaApi.map { produtoApi ->
                    // transforma o nome da imagem que vem do banco em um id de recurso do android
                    val resId = resources.getIdentifier(produtoApi.imagem ?: "", "drawable", packageName)
                    // se nao encontrar a imagem, usa o sofa_l por padrao para nao quebrar
                    val imagemCorreta = if (resId != 0) resId else R.drawable.sofa_l

                    Produto(
                        nome = produtoApi.nome,
                        preco = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(produtoApi.preco),
                        imagemResId = imagemCorreta
                    )
                }

                filtrarCatalogo("", "Todos")
                
            } catch (e: Exception) {
                Log.e("ProdutosActivity", "Erro ao chamar produtos.php", e)
            }
        }

        // logica de pesquisa e filtros
        var filtroAtual = "Todos"

        etBusca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrarCatalogo(s.toString(), filtroAtual)
            }
        })

        fun resetarBotoes() {
            btnFiltroTodos.backgroundTintList = ContextCompat.getColorStateList(this@ProdutosActivity, R.color.white)
            btnFiltroTodos.setTextColor(ContextCompat.getColor(this@ProdutosActivity, R.color.rubye_fonte_escura))
            
            btnFiltroRetro.backgroundTintList = ContextCompat.getColorStateList(this@ProdutosActivity, R.color.white)
            btnFiltroRetro.setTextColor(ContextCompat.getColor(this@ProdutosActivity, R.color.rubye_fonte_escura))
            
            btnFiltroPremium.backgroundTintList = ContextCompat.getColorStateList(this@ProdutosActivity, R.color.white)
            btnFiltroPremium.setTextColor(ContextCompat.getColor(this@ProdutosActivity, R.color.rubye_fonte_escura))
        }

        fun ativarBotao(btn: Button) {
            btn.backgroundTintList = ContextCompat.getColorStateList(this@ProdutosActivity, R.color.rubye_gold)
            btn.setTextColor(ContextCompat.getColor(this@ProdutosActivity, R.color.white))
        }

        btnFiltroTodos.setOnClickListener {
            filtroAtual = "Todos"
            resetarBotoes()
            ativarBotao(btnFiltroTodos)
            filtrarCatalogo(etBusca.text.toString(), filtroAtual)
        }

        btnFiltroRetro.setOnClickListener {
            filtroAtual = "Retrô"
            resetarBotoes()
            ativarBotao(btnFiltroRetro)
            filtrarCatalogo(etBusca.text.toString(), filtroAtual)
        }

        btnFiltroPremium.setOnClickListener {
            filtroAtual = "Premium"
            resetarBotoes()
            ativarBotao(btnFiltroPremium)
            filtrarCatalogo(etBusca.text.toString(), filtroAtual)
        }

        // Botoes das novas seções (Personalizado e Leilao)
        val btnMenuPersonalizado = findViewById<Button>(R.id.btnMenuPersonalizado)
        val btnMenuLeilao = findViewById<Button>(R.id.btnMenuLeilao)

        btnMenuPersonalizado.setOnClickListener {
            val intent = Intent(this, PersonalizadoActivity::class.java)
            startActivity(intent)
        }

        btnMenuLeilao.setOnClickListener {
            val intent = Intent(this, LeilaoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun filtrarCatalogo(busca: String, categoria: String) {
        val listaFiltrada = listaCompleta.filter { produto ->
            // filtra pelo nome digitado na barra de pesquisa
            val matchBusca = produto.nome.contains(busca, ignoreCase = true)
            
            // filtra pela "categoria" baseada em palavras-chave no nome do produto
            val matchCategoria = when (categoria) {
                "Todos" -> true
                "Retrô" -> produto.nome.contains("Retro", ignoreCase = true) || produto.nome.contains("Renascentist", ignoreCase = true)
                "Premium" -> produto.nome.contains("Premium", ignoreCase = true) || produto.nome.contains("L", ignoreCase = true)
                else -> true
            }

            matchBusca && matchCategoria
        }
        
        // atualiza a lista na tela
        rvProdutos.adapter = ProdutoAdapter(listaFiltrada)
    }
}