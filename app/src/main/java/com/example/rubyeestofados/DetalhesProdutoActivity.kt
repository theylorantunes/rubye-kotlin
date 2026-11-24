package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class DetalhesProdutoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_produto)

        val vpFotos = findViewById<ViewPager2>(R.id.vpFotosDetalhe)
        val tvContador = findViewById<TextView>(R.id.tvContadorFotos)
        
        val tvNome = findViewById<TextView>(R.id.tvNomeDetalhe)
        val tvPreco = findViewById<TextView>(R.id.tvPrecoDetalhe)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnComprar = findViewById<Button>(R.id.btnComprar)
        val btnIrCarrinho = findViewById<ImageView>(R.id.btnIrCarrinho)
        val rvAvaliacoes = findViewById<RecyclerView>(R.id.rvAvaliacoes)

        val nome = intent.getStringExtra("NOME_PRODUTO") ?: "Produto sem nome"
        val preco = intent.getStringExtra("PRECO_PRODUTO") ?: "R$ 0,00"
        val imagemResId = intent.getIntExtra("IMAGEM_PRODUTO", R.drawable.logo_rubye)
        val imagemUrl = intent.getStringExtra("IMAGEM_URL_PRODUTO")

        tvNome.text = nome
        tvPreco.text = preco

        // --- MOCK DE AVALIAÇÕES DO PRODUTO ---
        val listaReviews = listOf(
            Review("Marcos Silva", 5.0, "Sofá sensacional! O couro é de altíssima qualidade e ficou perfeito na minha sala."),
            Review("Ana Clara", 4.5, "Muito bonito e confortável, mas a entrega atrasou 2 dias. Fora isso, recomendo!"),
            Review("Felipe Souza", 5.0, "Atendimento impecável da Rubye. A cor do tecido é exatamente igual à foto.")
        )
        rvAvaliacoes.layoutManager = LinearLayoutManager(this)
        rvAvaliacoes.adapter = ReviewAdapter(listaReviews)
        // -------------------------------------

        val listaImagens = mutableListOf<String>()
        
        if (!imagemUrl.isNullOrEmpty()) {
            listaImagens.add(imagemUrl)
            // Mock de imagens extras na nuvem pra exibir o carrossel funcionando!
            listaImagens.add("https://images.unsplash.com/photo-1505693314120-0d443867891c?q=80&w=800&auto=format&fit=crop")
            listaImagens.add("https://images.unsplash.com/photo-1493663284031-b7e3aefcae8e?q=80&w=800&auto=format&fit=crop")
        } else {
            // Se nao tem URL no banco, a gente joga um sinal pra ele usar o ResId local
            listaImagens.add("local")
        }

        vpFotos.adapter = ImageCarouselAdapter(listaImagens, imagemResId)
        tvContador.text = "1/${listaImagens.size}"

        vpFotos.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tvContador.text = "${position + 1}/${listaImagens.size}"
            }
        })

        btnVoltar.setOnClickListener {
            finish()
        }

        btnIrCarrinho.setOnClickListener {
            val intent = android.content.Intent(this, CarrinhoActivity::class.java)
            startActivity(intent)
        }

        btnComprar.setOnClickListener {
            val produto = Produto(nome, preco, imagemResId)
            CartManager.adicionarProduto(produto)
            Toast.makeText(this, "Produto adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            // volta pro catalogo ou deixa continuar navegando (opcional fechar a tela com finish())
            finish()
        }
    }
}