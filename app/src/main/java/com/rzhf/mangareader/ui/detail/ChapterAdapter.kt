package com.rzhf.mangareader.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.api.ChapterData

class ChapterAdapter(
    private val chapterList: List<ChapterData>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tvChapterNumber)
        val tvTitle: TextView = itemView.findViewById(R.id.tvChapterTitle)

        fun bind(chapter: ChapterData) {
            val chNum = chapter.attributes.chapter ?: "-"
            val chTitle = chapter.attributes.title ?: "Tanpa Judul"

            tvNumber.text = "Chapter $chNum"
            tvTitle.text = chTitle

            itemView.setOnClickListener {
                onItemClick(chapter.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(chapterList[position])
    }

    override fun getItemCount(): Int = chapterList.size
}