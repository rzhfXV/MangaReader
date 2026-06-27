package com.rzhf.mangareader.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val tvDesc = view.findViewById<TextView>(R.id.tvDetailDesc)
        val btnViewChapters = view.findViewById<Button>(R.id.btnViewChapters)

        val mangaId = arguments?.getString("mangaId") ?: return

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getMangaDetail(mangaId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val manga = response.body()!!.data

                        val titleStr = manga.attributes.title["en"] ?: manga.attributes.title.values.firstOrNull() ?: "Tanpa Judul"
                        val descStr = manga.attributes.description?.get("en") ?: "Sinopsis tidak tersedia."

                        tvTitle.text = titleStr
                        tvDesc.text = descStr

                        btnViewChapters.setOnClickListener {
                            val bundle = Bundle().apply { putString("mangaId", mangaId) }
                            findNavController().navigate(R.id.action_detail_to_chapterList, bundle)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat detail", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}