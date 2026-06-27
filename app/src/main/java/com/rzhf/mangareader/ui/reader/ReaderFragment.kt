package com.rzhf.mangareader.ui.reader

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReaderFragment : Fragment(R.layout.fragment_reader) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvReader = view.findViewById<RecyclerView>(R.id.rvReader)
        val pbReader = view.findViewById<ProgressBar>(R.id.pbReader)

        rvReader.layoutManager = LinearLayoutManager(requireContext())

        val chapterId = arguments?.getString("chapterId") ?: return

        pbReader.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getChapterPages(chapterId, forcePort443 = true)
                withContext(Dispatchers.Main) {
                    pbReader.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        val atHomeData = response.body()!!

                        val hash = atHomeData.chapter.hash
                        val baseUrl = atHomeData.baseUrl

                        val imageUrls = atHomeData.chapter.dataSaver.map { fileName ->
                            "$baseUrl/data-saver/$hash/$fileName"
                        }

                        imageUrls.forEach { url ->
                            android.util.Log.d("FINAL_URL_CHECK", url)
                        }

                        rvReader.adapter = PageAdapter(imageUrls)
                    } else {
                        Toast.makeText(requireContext(), "Gagal memuat halaman", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    pbReader.visibility = View.GONE
                    Toast.makeText(requireContext(), "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}