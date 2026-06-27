package com.rzhf.mangareader.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var rvMangaList: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMangaList = view.findViewById(R.id.rvMangaList)
        progressBar = view.findViewById(R.id.progressBar)

        // Tampilkan list dalam bentuk Grid (2 kolom)
        rvMangaList.layoutManager = GridLayoutManager(requireContext(), 2)

        setupNavigationButtons(view)
        fetchMangaList()
    }

    private fun setupNavigationButtons(view: View) {
        view.findViewById<ImageButton>(R.id.btnSearch).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_search)
        }
        view.findViewById<ImageButton>(R.id.btnProfile).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_profile)
        }
        view.findViewById<ImageButton>(R.id.btnAbout).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_about)
        }
    }

    private fun fetchMangaList() {
        progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getMangaList()

                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        val mangaList = response.body()!!.data
                        val adapter = MangaAdapter(mangaList) { mangaId ->
                            // Pindah ke DetailFragment dengan membawa mangaId
                            val bundle = Bundle().apply { putString("mangaId", mangaId) }
                            findNavController().navigate(R.id.action_home_to_detail, bundle)
                        }
                        rvMangaList.adapter = adapter
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}