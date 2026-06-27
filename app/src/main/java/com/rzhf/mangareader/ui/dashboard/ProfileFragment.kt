package com.rzhf.mangareader.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rzhf.mangareader.R
import com.rzhf.mangareader.data.auth.AuthRepository

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authRepository = AuthRepository()
        val tvUserEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val currentUser = authRepository.getCurrentUser()
        tvUserEmail.text = currentUser?.email ?: "Guest"

        btnLogout.setOnClickListener {
            authRepository.logout()
            Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_profile_to_login)
        }
    }
}