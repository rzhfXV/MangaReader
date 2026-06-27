package com.rzhf.mangareader.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChapterListFragment : Fragment(R.layout.fragment_chapter_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvChapterList = view.findViewById<RecyclerView>(R.id.rvChapterList)
        rvChapterList.layoutManager = LinearLayoutManager(requireContext())

        val mangaId = arguments?.getString("mangaId") ?: return

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getMangaFeed(mangaId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val chapterList = response.body()!!.data
                        val adapter = ChapterAdapter(chapterList) { chapterId ->
                            val bundle = Bundle().apply { putString("chapterId", chapterId) }
                            findNavController().navigate(R.id.action_chapterList_to_reader, bundle)
                        }
                        rvChapterList.adapter = adapter
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat chapter", Toast.LENGTH_SHORT).show()
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