package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PersonalizadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizado)

        BottomNavHelper.setupBottomNav(this, R.id.nav_personalizado)

        val spTamanho = findViewById<Spinner>(R.id.spTamanho)
        val spEstilo = findViewById<Spinner>(R.id.spEstilo)
        val spMaterial = findViewById<Spinner>(R.id.spMaterial)
        val spMadeira = findViewById<Spinner>(R.id.spMadeira)
        val btnSolicitar = findViewById<Button>(R.id.btnSolicitarOrcamento)
        val btnVoltar = findViewById<Button>(R.id.btnVoltarCatalogoPers)

        // Populando os Spinners
        val tamanhos = arrayOf("2 Lugares (1,50m)", "3 Lugares (2,00m)", "4 Lugares (2,50m)", "Em L (Retrátil)")
        val estilos = arrayOf("Retrô", "Premium", "Minimalista", "Industrial")
        val materiais = arrayOf("Couro Sintético", "Couro Legítimo", "Linho", "Veludo")
        val madeiras = arrayOf("Carvalho (Clara)", "Mogno (Escura)", "Pinus", "Eucalipto")

        spTamanho.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tamanhos)
        spEstilo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estilos)
        spMaterial.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, materiais)
        spMadeira.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, madeiras)

        btnSolicitar.setOnClickListener {
            Toast.makeText(this, "Orçamento solicitado com sucesso! Nossa equipe entrará em contato.", Toast.LENGTH_LONG).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
