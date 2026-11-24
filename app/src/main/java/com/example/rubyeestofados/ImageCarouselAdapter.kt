package com.example.rubyeestofados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageCarouselAdapter(
    private val imagensUrls: List<String>,
    private val fallbackResId: Int
) : RecyclerView.Adapter<ImageCarouselAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.ivCarousel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = imagensUrls[position]

        if (url == "local") {
            holder.imageView.setImageResource(fallbackResId)
        } else {
            Glide.with(holder.itemView.context)
                .load(url)
                .placeholder(R.drawable.sofa_l)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int = imagensUrls.size
}