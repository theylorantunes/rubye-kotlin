package com.example.rubyeestofados

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)


        val etNome = findViewById<EditText>(R.id.etNomeCad)
        val etEmail = findViewById<EditText>(R.id.etEmailCad)
        val etSenha = findViewById<EditText>(R.id.etSenhaCad)
        val etConfirmaSenha = findViewById<EditText>(R.id.etConfirmaSenhaCad)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)
        val btnVoltarLogin = findViewById<Button>(R.id.btnVoltarLogin)


        btnVoltarLogin.setOnClickListener {
            finish()
        }


        btnCadastrar.setOnClickListener {
            val nome = etNome.text.toString()
            val email = etEmail.text.toString()
            val senha = etSenha.text.toString()
            val confirmaSenha = etConfirmaSenha.text.toString()

            if (nome.isNotBlank() && email.isNotBlank() && senha.isNotBlank() && confirmaSenha.isNotBlank()) {
                if (senha == confirmaSenha) {
                    fazerCadastroApi(nome, email, senha)
                } else {
                    Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fazerCadastroApi(nome: String, email: String, senha: String) {
        lifecycleScope.launch {
            try {
                val request = CadastroRequest(nome, email, senha)

                val response: retrofit2.Response<AuthResponse> = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.realizarCadastro(request)
                }

                if (response.isSuccessful && response.body()?.sucesso == true) {
                    Toast.makeText(this@CadastroActivity, "Conta criada! Faça login.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@CadastroActivity, "Falha ao cadastrar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CadastroActivity, "Erro de rede: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}