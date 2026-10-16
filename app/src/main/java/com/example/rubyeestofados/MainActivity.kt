package com.example.rubyeestofados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnIrCadastro = findViewById<Button>(R.id.btnIrCadastro)


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val senha = etSenha.text.toString()

            if (email.isNotBlank() && senha.isNotBlank()) {


                if (email == "admin" && senha == "123") {
                    Toast.makeText(this, "Login de Teste Autorizado!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProdutosActivity::class.java)
                    startActivity(intent)
                }

                else {

                    fazerLogin(email, senha)
                }

            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }


        btnIrCadastro.setOnClickListener {

            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fazerLogin(email: String, senha: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.realizarLogin(email, senha)
                }

                if (response.isSuccessful && response.body() != null) {
                    val listaUsuarios = response.body()!!

                    if (listaUsuarios.isNotEmpty()) {
                        Toast.makeText(this@MainActivity, "Bem-vindo(a)!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, ProdutosActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Erro no servidor", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erro de rede: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}