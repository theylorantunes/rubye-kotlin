package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LeilaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leilao)

        BottomNavHelper.setupBottomNav(this, R.id.nav_leilao)

        val rvLeiloes = findViewById<RecyclerView>(R.id.rvLeiloes)

        val listaLeiloes = listOf(
            ItemLeilao(1, "Sofá Modular 'Factory' (Único)", 1500.0, "00:15:30", R.drawable.sofa_l),
            ItemLeilao(2, "Sofá Retrô 'Neon Night' (Custom)", 1850.0, "02:40:15", R.drawable.sofa_renan),
            ItemLeilao(3, "Poltrona 'Red Velvet' (CSFloat Edition)", 800.0, "05:10:00", R.drawable.sofa_amem)
        )

        rvLeiloes.layoutManager = LinearLayoutManager(this)
        rvLeiloes.adapter = LeilaoAdapter(listaLeiloes)
    }
}
