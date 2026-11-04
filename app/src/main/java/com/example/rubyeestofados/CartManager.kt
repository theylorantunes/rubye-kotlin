package com.example.rubyeestofados

data class ItemCarrinho(val produto: Produto, var quantidade: Int)

object CartManager {
    private val itens = mutableListOf<ItemCarrinho>()

    fun getItens(): MutableList<ItemCarrinho> = itens

    fun adicionarProduto(produto: Produto) {
        val itemExistente = itens.find { it.produto.nome == produto.nome }
        if (itemExistente != null) {
            itemExistente.quantidade++
        } else {
            itens.add(ItemCarrinho(produto, 1))
        }
    }

    fun removerProduto(produto: Produto) {
        itens.removeAll { it.produto.nome == produto.nome }
    }

    fun limparCarrinho() {
        itens.clear()
    }

    fun getTotal(): Double {
        return itens.sumOf { item ->
            // string chega como "R$ 899,00", remove o R$ e os espaços
            var precoLido = item.produto.preco.replace("R$", "").replace(" ", "").trim()
            
            // o kotlin espera formato americano "899.00".
            // remove o ponto de milhar se existir.
            precoLido = precoLido.replace(".", "")
            
            // troca a virgula dos centavos por um ponto
            precoLido = precoLido.replace(",", ".")
            
            val preco = precoLido.toDoubleOrNull() ?: 0.0
            preco * item.quantidade
        }
    }
}
