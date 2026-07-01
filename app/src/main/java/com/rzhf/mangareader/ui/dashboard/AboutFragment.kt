package com.rzhf.mangareader.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.rzhf.mangareader.R

class AboutFragment : Fragment(R.layout.fragment_about) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGithub = view.findViewById<Button>(R.id.btnGithub)
        btnGithub.setOnClickListener {
            val url = "https://github.com/rzhfXV/MangaReader"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}