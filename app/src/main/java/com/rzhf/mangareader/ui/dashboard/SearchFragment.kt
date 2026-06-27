package com.rzhf.mangareader.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearchQuery = view.findViewById<EditText>(R.id.etSearchQuery)
        val btnDoSearch = view.findViewById<Button>(R.id.btnDoSearch)
        val pbSearch = view.findViewById<ProgressBar>(R.id.pbSearch)
        val rvSearchList = view.findViewById<RecyclerView>(R.id.rvSearchList)

        rvSearchList.layoutManager = LinearLayoutManager(requireContext())

        btnDoSearch.setOnClickListener {
            val query = etSearchQuery.text.toString().trim()
            if (query.isNotEmpty()) {
                pbSearch.visibility = View.VISIBLE

                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val response = RetrofitClient.instance.getMangaList(title = query)
                        withContext(Dispatchers.Main) {
                            pbSearch.visibility = View.GONE
                            if (response.isSuccessful && response.body() != null) {
                                val searchResult = response.body()!!.data
                                val adapter = MangaAdapter(searchResult) { mangaId ->
                                    val bundle = Bundle().apply { putString("mangaId", mangaId) }
                                    findNavController().navigate(R.id.action_search_to_detail, bundle)
                                }
                                rvSearchList.adapter = adapter
                            } else {
                                Toast.makeText(requireContext(), "Manga tidak ditemukan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            pbSearch.visibility = View.GONE
                            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}