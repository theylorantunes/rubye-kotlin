package com.example.rubyeestofados

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class AdminLeiloesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_leiloes)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarAdminLeiloes)
        val btnNovoLeilao = findViewById<ExtendedFloatingActionButton>(R.id.btnNovoLeilao)
        val rvAdminLeiloes = findViewById<RecyclerView>(R.id.rvAdminLeiloes)

        btnVoltar.setOnClickListener { finish() }

        btnNovoLeilao.setOnClickListener {
            Toast.makeText(this, "Em breve: Tela de Inserir Novo Leilão!", Toast.LENGTH_SHORT).show()
        }

        val listaLeiloes = mutableListOf(
            ItemLeilao(1, "Sofá Modular 'Factory' (Único)", 1500.0, "00:15:30", R.drawable.sofa_l),
            ItemLeilao(2, "Sofá Retrô 'Neon Night' (Custom)", 1850.0, "02:40:15", R.drawable.sofa_renan),
            ItemLeilao(3, "Poltrona 'Red Velvet' (CSFloat Edition)", 800.0, "05:10:00", R.drawable.sofa_amem)
        )

        rvAdminLeiloes.layoutManager = LinearLayoutManager(this)
        rvAdminLeiloes.adapter = AdminLeiloesAdapter(listaLeiloes)
    }
}
