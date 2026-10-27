package com.example.rubyeestofados

import android.app.Activity
import android.content.Intent
import android.widget.TextView

object HeaderHelper {

    fun setupHeader(activity: Activity) {
        val navProdutos = activity.findViewById<TextView>(R.id.navProdutos)
        val navCarrinho = activity.findViewById<TextView>(R.id.navCarrinho)
        val navConta = activity.findViewById<TextView>(R.id.navConta)

        navProdutos?.setOnClickListener {
            if (activity !is ProdutosActivity) {
                val intent = Intent(activity, ProdutosActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                activity.startActivity(intent)
            }
        }

        navCarrinho?.setOnClickListener {
            if (activity !is CarrinhoActivity) {
                val intent = Intent(activity, CarrinhoActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                activity.startActivity(intent)
            }
        }

        navConta?.setOnClickListener {
            if (activity !is MinhaContaActivity) {
                val intent = Intent(activity, MinhaContaActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                activity.startActivity(intent)
            }
        }
    }
}