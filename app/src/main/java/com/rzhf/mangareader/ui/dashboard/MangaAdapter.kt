package com.rzhf.mangareader.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.api.MangaData

class MangaAdapter(
    private val mangaList: List<MangaData>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    inner class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvMangaTitle)
        val ivCover: ImageView = itemView.findViewById(R.id.ivCover) // Tambahkan inisialisasi ImageView

        fun bind(manga: MangaData) {
            val titleStr = manga.attributes.title["en"] ?: manga.attributes.title.values.firstOrNull() ?: "No Title"
            tvTitle.text = titleStr

            // LOGIKA LOAD COVER
            val coverRelationship = manga.relationships?.find { it.type == "cover_art" }
            val fileName = coverRelationship?.attributes?.fileName

            if (fileName != null) {
                val coverUrl = "https://uploads.mangadex.org/covers/${manga.id}/$fileName"

                Glide.with(itemView.context)
                    .load(coverUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivCover)
            } else {
                // Kosongkan gambar jika tidak ada cover
                ivCover.setImageDrawable(null)
            }

            itemView.setOnClickListener {
                onItemClick(manga.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
        return MangaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.bind(mangaList[position])
    }

    override fun getItemCount(): Int = mangaList.size
}