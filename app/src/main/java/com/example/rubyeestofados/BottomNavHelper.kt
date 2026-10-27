package com.example.rubyeestofados

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavHelper {

    fun setupBottomNav(activity: Activity, currentItemId: Int) {
        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        // Define o item selecionado corretamente para a tela atual
        bottomNav?.selectedItemId = currentItemId
        
        bottomNav?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_produtos -> {
                    if (currentItemId != R.id.nav_produtos) {
                        val intent = Intent(activity, ProdutosActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_carrinho -> {
                    if (currentItemId != R.id.nav_carrinho) {
                        val intent = Intent(activity, CarrinhoActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_conta -> {
                    if (currentItemId != R.id.nav_conta) {
                        val intent = Intent(activity, MinhaContaActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        activity.startActivity(intent)
                    }
                    true
                }
                else -> false
            }
        }
    }
}