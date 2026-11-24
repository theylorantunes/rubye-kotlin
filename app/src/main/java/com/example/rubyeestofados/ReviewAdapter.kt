package com.example.rubyeestofados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Review(val autor: String, val nota: Double, val texto: String)

class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAutor: TextView = view.findViewById(R.id.tvReviewAutor)
        val tvNota: TextView = view.findViewById(R.id.tvReviewNota)
        val tvTexto: TextView = view.findViewById(R.id.tvReviewTexto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.tvAutor.text = review.autor
        holder.tvNota.text = review.nota.toString()
        holder.tvTexto.text = review.texto
    }

    override fun getItemCount() = reviews.size
}
